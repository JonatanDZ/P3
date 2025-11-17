var tools = ["tool1","tool2","tool3", "Atool", "Btool","Ctool"];

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
        for (i = 0; i < arr.length; i++) {
            if (arr[i].substring(0, val.length).toUpperCase() === val.toUpperCase()) {
                b = document.createElement("DIV");
                var heading = document.createElement('b')
                heading.appendChild(document.createTextNode(arr[i].substring(0, val.length)+arr[i].substring(val.length)))
                b.appendChild(heading);
                for(let l = 0; l<arr.length;l++) {
                    u = document.createElement("DIV");
                    var subheading = document.createElement('p')
                    subheading.appendChild(document.createTextNode(arr[l]));
                    u.appendChild(subheading)
                    var input = document.createElement('input')
                    input.setAttribute("type","hidden");
                    input.setAttribute("value", arr[l])
                    u.appendChild(input)
                    u.addEventListener("click", function(e) {
                        inp.value = this.getElementsByTagName("input")[0].value;
                        closeAllLists();
                    });
                    b.appendChild(u)
                }


                /*b.innerHTML = "<strong>" + arr[i].substring(0, val.length) + "</strong>";
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
searchbar(document.getElementById("myInput"),tools)