import {toggleForm, displayURLbar} from "./toggleForm.js";
import {loadOptions, loadTags} from "./loadOptions.js";
import {submitForm} from "./submitForm.js";

let toggleBtns;
let addToolDiv;
let dynamicCheck;

let formIsShown = false;

document.addEventListener("DOMContentLoaded", ()=>{
    loadAllFromOptions();

    //  make sure the DOM element is available as a global for toggleForm.js
    window.addToolDiv = document.querySelector("#addToolDiv");
    addToolDiv = window.addToolDiv;
    toggleBtns = document.querySelectorAll(".toggleBtn");
    toggleBtns.forEach(btn => {
            btn.addEventListener("click", (event)=>{
                event.stopPropagation(); // prevents the opening click from being treated as an outside click.
                formIsShown = toggleForm(formIsShown);

                if(formIsShown){
                        document.addEventListener("click", handleOutsideClick)
                } else {
                    document.removeEventListener("click", handleOutsideClick)
                }
            });
        }
    )
    dynamicCheck = document.querySelector("#isDynamic");
    dynamicCheck.addEventListener("change", ()=>{
        displayURLbar(dynamicCheck.checked);
    });
});


function loadAllFromOptions(){
    loadOptions("departments");
    loadOptions("jurisdictions");
    loadTags();
}


if (document.querySelector("#submitBtn")) {
    document.querySelector("#submitBtn").addEventListener("click", function (e) {
        e.preventDefault();
        submitForm();
    });
}

function handleOutsideClick(event) {
    // if addToolDiv is not found, do nothing
    if (!addToolDiv) return;

    // if click outside the form and not on a "toggle button", close the form
    if (
        formIsShown &&
        !addToolDiv.contains(event.target) &&
        // allow clicks on the toggle buttons (open/cancel) to behave normally
        !event.target.closest(".toggleBtn")
    ) {
        formIsShown = toggleForm(formIsShown);
        document.removeEventListener("click", handleOutsideClick);
    }
}

