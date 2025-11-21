let toolsArr = ["tool1","tool2","tool3"];
let tagsArr =["tag1","tag2","tag3","Atag", "Btag"];
function searchbar(inp, arr) {
    var currentFocus;
    inp.addEventListener("input", function(e) {
        var a, b, u, i, val = this.value;
        closeAllLists();
        if (!val) { return false;}
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "searchbar-list");
        a.setAttribute("class", "searchbar-items");
        this.parentNode.appendChild(a);
        //Checkes all element in the array if they match the searched input
        for (i = 0; i < arr.length; i++) {
            //Checks to see if the input is included in any of the elements in the array 'tags'
             if (arr[i].value.toUpperCase().includes(val.toUpperCase())){
                 b = document.createElement("DIV");
                 b.setAttribute("class", "searchbar-heading");
                 var heading = document.createElement('b')
                 heading.appendChild(document.createTextNode(arr[i].value.substring(0, val.length)+arr[i].value.substring(val.length)))

                 //append tools, to the tag, so all tools will be appended to each tag (Should be conditioned to only suitible tools by tags)
                 b.appendChild(heading);

                 if (Array.isArray(arr[i].tools)) {
                     for (let l = 0; l < arr[i].tools.length; l++) {
                         u = document.createElement("DIV");
                         u.setAttribute("class", "searchbar-subheading");
                         var subheading = document.createElement('p')

                         //show name and URL
                         subheading.appendChild(document.createTextNode(arr[i].tools[l].name));
                         u.appendChild(subheading)

                         var input = document.createElement('input')
                         input.setAttribute("type", "hidden");
                         input.setAttribute("value", arr[l])
                         u.appendChild(input)
                         u.addEventListener("click", function (e) {
                             inp.value = this.getElementsByTagName("input")[0].value;
                             closeAllLists();
                         });
                         b.appendChild(u)

                     }
                 }


                /* OBS der er ændre ifht. array navn AKA tag stilling til om det er tool el. tag
                b.innerHTML = "<strong>" + arr[i].substring(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substring(val.length);
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";*/


                a.appendChild(b);
            }
        }
    });

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
    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("searchbar-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });


}
async function loadTags() {
    try{
        const response = await fetch('/tags');
        const tagsJson = await response.json();

        searchbar(document.getElementById("myInput"), tagsJson)

    } catch (err){
        console.error("failed to load tags:", err);
    }
}
loadTags();

//searchbar(document.getElementById("myInput"),loadTags, tools)