import {toggleForm, displayURLbar, displayReview, toggleCards} from "./toggleForm.js";
import {loadOptions, enableTagSearch} from "./loadOptions.js";
import {submitForm, submitTag} from "./submitForm.js";

let toggleBtns;
let addToolDiv;
let dynamicCheck;
let toggleCard = 1;

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
    enableTagSearch();
    setupPersonalToggle()
}


if (document.querySelector("#submitBtn")) {
    document.querySelector("#submitBtn").addEventListener("click",  (e) => {
        e.preventDefault();
        submitForm();
    });
}

if (document.querySelector("#submitTagBtn")) {
    document.querySelector("#submitTagBtn").addEventListener("click", async (e)  =>  {
        e.preventDefault();
        await submitTag();
    })
}

if (document.querySelector(".toggleCardBtn")) {
    document.querySelectorAll(".toggleCardBtn").forEach(btn =>{
        btn.addEventListener("click", (e)=>{
            e.preventDefault();
            displayReview();
            toggleCard = toggleCards(btn.value, toggleCard);
        });
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

let allowedCards = [1,2,3,4,5,6];

export function updateAllowedCards(){
    const isPersonal = document.querySelector("#isPersonal");

    if (isPersonal) {
        allowedCards = [1,2,4,5]
    } else {
        allowedCards = [1,2,3,4,5,6];
    }

    document.querySelector("#isPersonal").addEventListener("change", (e)=>{
        updateAllowedCards();
    })
}



/* function setupPersonalToggle() {
    const personalCheck = document.querySelector("#isPersonal");

    const cardsToShow = [1,2,4,5];
    const cardsToHide = [3,6];

    function updateCards() {
        const isPersonal = personalCheck.checked;

        if (isPersonal) {
            cardsToHide.forEach(num => {
                document.querySelector(`#formCard${num}`).style.display = "none";
            });

            cardsToShow.forEach(num => {
                document.querySelector(`#formCard${num}`).style.display = "block";
            });
        } else {
            document.querySelectorAll(".formCards").forEach(card => {
                card.style.display = "block";
            });
        }
    }
    personalCheck.addEventListener("change", updateCards);
    //updateCards();
} */
