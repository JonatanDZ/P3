import {MakeToolJSON} from "./fetchTool.js";
import {toggleForm} from "./toggleForm.js";
import {loadOptions} from "./loadOptions.js";
import {submitForm} from "./submitForm.js";

let toggleBtns;
let addToolDiv;
let submitBtn;

let formIsShown = false;


document.addEventListener("DOMContentLoaded", ()=>{
    loadAllFromOptions();
    addToolDiv = document.querySelector("#addToolDiv");
    toggleBtns = document.querySelectorAll(".toggleBtn");
    toggleBtns.forEach(btn => {
            btn.addEventListener("click", ()=>{
                formIsShown = toggleForm(formIsShown);
            });
        }
    )
});



function loadAllFromOptions(){
    loadOptions("departments");
    loadOptions("jurisdictions");
}


/*
window.onclick = function(event) {
    if (event.target == addToolDiv) {
        addToolDiv.style.display = "none";
    }
}
*/

//
if (document.querySelector("#submitBtn")) {
    document.querySelector("#submitBtn").addEventListener("click", function (e) {
        e.preventDefault();
        submitForm();
    });
}


