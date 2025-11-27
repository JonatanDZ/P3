//HAVE USED W3SCHOOLS EXAMPLE FOR THIS:
//https://www.w3schools.com/howto/howto_js_autocomplete.asp

import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {fuzzySearch} from "./fuzzySearch.js";

async function searchbar(inp, arr) {
    const employee = await getCurrentEmployee();
    var currentFocus;
    inp.addEventListener("input", function(e) {
        let searchBarList, searchBarItem;
        //Input from searchbar
        let val = this.value;
        //Close potential already opened list
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1
        //Create div for whole list
        searchBarList = document.createElement("DIV");
        searchBarList.setAttribute("id", this.id + "searchbar-list");
        searchBarList.setAttribute("class", "searchbar-items");
        //Append the list to searchbar
        this.parentNode.appendChild(searchBarList);
        //Checkes all element in the array if they match the searched input
        for (let i = 0; i < arr.length; i++) {
            //Checks to see if the input is included in any of the elements in the array 'tags'
             if (fuzzySearch(val, arr[i])){
                //show tool
                searchBarItem = showToolInSearchBar(arr[i], searchBarItem);

                if (!arr[i].is_personal){
                    searchBarItem = showTagsInSearchBar(arr[i].tags, searchBarItem);
                }

                searchBarList.appendChild(searchBarItem);
            }
        }
    });

    //The focus funktion. Makes it possible to use the arrows to go though tools
    inp.addEventListener("keydown", function(e) {
        var x = document.getElementById(this.id + "searchbar-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) { //down
            currentFocus++;
            addActive(x);
        } else if (e.keyCode == 38) { //up
            currentFocus--;
            addActive(x);
        } else if (e.keyCode == 13) { //enter
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });
    //Makes tool either active or nonactive. Used when pressing arrows
    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("searchbar-active");
    }
    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("searchbar-active");
        }
    }

    //Closes the list of tags and tools
    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("searchbar-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    //When clicking somewhere on screen close list
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });


}


export async function setUpSearchBar() {
    try{
        const response = await fetch('/tools');
        const tagsJson = await response.json();

        //Calls the function
        searchbar(document.getElementById("myInput"), tagsJson)

    } catch (err){
        console.error("failed to load tools in searchbar:", err);
    }
}

function showToolInSearchBar(tool, parentElement){
    //Make a container we can store name and url in
    const nameUrlContainer = document.createElement("div");
    nameUrlContainer.className = "nameUrlContainer";
    //show name
    parentElement = document.createElement("div");
    parentElement.setAttribute("class", "searchbar-heading");
    const toolName = document.createElement('p')
    toolName.textContent = tool.name + ": ";
    
    // append toolName to the div
    nameUrlContainer.appendChild(toolName);

    //show URL
    const ToolLink = document.createElement("a");
    //if link is dynamic showcase it correctly for the users
    if (tool.is_dynamic){
        ToolLink.href = tool.url.replace('$USER$', employee.initials.toLowerCase());
        ToolLink.textContent = tool.url.replace('$USER$', employee.initials.toLowerCase());
    } else {
        ToolLink.href = tool.url;
        ToolLink.textContent = tool.url;
    }
    ToolLink.target = "_blank"; //Make the link open not in the current tab (new window or new tab)
    toolName.appendChild(ToolLink);

    parentElement.addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            ToolLink.click();
        }
    });
    parentElement.appendChild(nameUrlContainer);
    return parentElement;
}

function showTagsInSearchBar(tags, parentElement){
    //Create container for the tags
    const tagContainer = document.createElement("div"); 
    tagContainer.className = "tagContainer";

    for (let i = 0; i < tags.length; i++){
        // Create chip container
        const chip = document.createElement("div");
        chip.className = "tag-chip";

        // Create tag label
        const label = document.createElement("span");
        label.textContent = tags[i];

        chip.appendChild(label);
        tagContainer.appendChild(chip);
    }

    parentElement.appendChild(tagContainer);

    return parentElement;
}

setUpSearchBar();