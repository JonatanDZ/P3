//HAVE USED W3SCHOOLS EXAMPLE FOR SOME OF THIS:
//https://www.w3schools.com/howto/howto_js_autocomplete.asp

import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {fuzzySearch} from "./fuzzySearch.js";

//searchbar using fuzzySearch and can tab trough with arrows
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
        currentFocus = -1 //where the "clicker" is (for when clicking enter) currently on the search bar
        //Create div for whole list
        searchBarList = document.createElement("DIV");
        searchBarList.setAttribute("id", this.id + "searchbar-list");
        searchBarList.setAttribute("class", "searchbar-list");
        //Append the list to searchbar
        this.parentNode.appendChild(searchBarList);
        //Checkes all element in the array if they match the searched input
        for (let i = 0; i < arr.length; i++) {
            //makes dynamic links searchable and showcaseable
            if(arr[i].is_dynamic){
                arr[i].url = arr[i].url.replace('$USER$', employee.initials.toLowerCase());
            }

            //Checks to see if the input is included in either the name, url or tags
             if (fuzzySearch(val, arr[i])){

                //show makes the whole div clickable
                const link = document.createElement("a");
                link.href = arr[i].url;
                link.target = "blank"; //Make the link open not in the current tab (new window or new tab)
                link.className = "focusable"; //so we can toggle the focus

                //show tool
                searchBarItem = showToolInSearchBar(arr[i], searchBarItem);

                if (!arr[i].is_personal){
                    searchBarItem = showTagsInDiv(arr[i].tags, searchBarItem);
                }

                searchBarList.appendChild(link);
                link.appendChild(searchBarItem); //
            }
        }
    });

    //The focus function. Makes it possible to use the arrows to go though tools
    inp.addEventListener("keydown", function(e) {
        let x = document.getElementById(this.id + "searchbar-list");
        if (x) x = x.querySelectorAll(".focusable");
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
        var x = document.getElementsByClassName("searchbar-list");
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
        const toolsJson = await response.json();

        //Calls the function
        searchbar(document.getElementById("myInput"), toolsJson)

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
    parentElement.setAttribute("class", "searchbar-item");
    const toolName = document.createElement('p')
    toolName.textContent = tool.name + ": ";
    
    // append toolName to the div
    nameUrlContainer.appendChild(toolName);

    //show URL
    const ToolLink = document.createElement("a");

    ToolLink.href = tool.url;
    ToolLink.textContent = tool.url;
    
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

export function showTagsInDiv(tags, parentElement){
    //Create container for the tags
    const tagContainer = document.createElement("div"); 
    tagContainer.className = "tagContainer";

    for (let i = 0; i < tags.length; i++){
        // Create chip container
        const chip = document.createElement("div");
        chip.className = "tag-chip";
        chip.style.backgroundColor = stringToColor(tags[i]);

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

//This function generates a hashcode that is used to generate colors for our tags
function generateHashCode(str){
    let hash = 0;
    for (const char of str) {
        hash = hash * 31 + char.charCodeAt(0); //hash = hash * 31 + (ASCII value of char) 
    }
    return hash;
}

export function stringToColor(str) {
    const hash = generateHashCode(str); //ensures that is always the same color you get from the same value;

    //we Modulo with 127 and add 127 to ensure that the colors are bright, so we can use black text on them.
    const red = hash % 127 + 127; 
    const green = (hash / 7) % 127 + 127; //we devide by prime numbers to avoid number patterns  
    const blue = (hash / 11) % 127 + 127; //we devide by prime numbers to avoid number patterns


     //converts it to a form js understands
    return `rgb(${red},${green},${blue})`
}