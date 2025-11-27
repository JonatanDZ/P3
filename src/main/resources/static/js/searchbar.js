//HAVE USED W3SCHOOLS EXAMPLE FOR THIS:
//https://www.w3schools.com/howto/howto_js_autocomplete.asp

import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {fuzzySearch} from "./fuzzySearch.js";

async function searchbar(inp, arr) {
    const employee = await getCurrentEmployee();
    var currentFocus;
    inp.addEventListener("input", function(e) {
        let a, b, u;
        //Input from searchbar
        let val = this.value;
        //Close potential already opened list
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1
        //Create div for whole list
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "searchbar-list");
        a.setAttribute("class", "searchbar-items");
        //Append the list to searchbar
        this.parentNode.appendChild(a);
        //Checkes all element in the array if they match the searched input
        for (let i = 0; i < arr.length; i++) {
            //Checks to see if the input is included in any of the elements in the array 'tags'
             if (fuzzySearch(val.toLowerCase(), arr[i].value.toLowerCase())){
                 //Create tag div and tag name
                 b = document.createElement("DIV");
                 b.setAttribute("class", "searchbar-heading");
                 var heading = document.createElement('b')
                 heading.appendChild(document.createTextNode(arr[i].value.substring(0, val.length)+arr[i].value.substring(val.length)))

                 //append tools, to the tag, so all tools will be appended to each tag (Should be conditioned to only suitible tools by tags)
                 b.appendChild(heading);

                 //Look for tools that has tag
                 if (Array.isArray(arr[i].tools)) {
                     for (let l = 0; l < arr[i].tools.length; l++) {
                         //Create tool div, tool name and url
                         u = document.createElement("div");
                         u.setAttribute("class", "searchbar-subheading");
                         var subheading = document.createElement('p')


                         //show name
                         subheading.appendChild(document.createTextNode(arr[i].tools[l].name + " - "));

                         //Show url and make it a link
                         const link = document.createElement("a");

                         if (arr[i].tools[l].is_dynamic){
                             link.href = arr[i].tools[l].url.replace('$USER$', employee.initials.toLowerCase());
                             link.textContent = arr[i].tools[l].url.replace('$USER$', employee.initials.toLowerCase());
                         } else {
                             link.href = arr[i].tools[l].url;
                             link.textContent = arr[i].tools[l].url;

                         }
                         console.log(arr[i].tools[l]);
                         link.target = "_blank"; //Make the link open not in the current tab (new window or new tab)
                         subheading.appendChild(link);

                         //When tool div is clicked open url
                         u.setAttribute("onclick",`location.href='${arr[i].tools[l].url}';`)

                         u.appendChild(subheading);

                         //When pressing enter while focus on div then open url
                         u.addEventListener("keypress", function(event) {
                             if (event.key === "Enter") {
                                 event.preventDefault();
                                 u.click();
                             }
                         });
                         b.appendChild(u)
                     }
                 }
                a.appendChild(b);
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

//Loads tags
async function loadTags() {
    try{
        const response = await fetch('/tags');
        const tagsJson = await response.json();

        //Calls the function
        searchbar(document.getElementById("myInput"), tagsJson)

    } catch (err){
        console.error("failed to load tags:", err);
    }
}
loadTags();

//searchbar(document.getElementById("myInput"),loadTags, tools)