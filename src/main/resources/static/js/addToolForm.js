import {MakeToolJSON} from "./fetchTool.js";
import {toggleForm} from "./toggleForm.js";
import {loadOptions} from "./loadOptions.js";

const toggleBtns = document.querySelectorAll(".toggleBtn");

let formIsShown = false;

toggleBtns.forEach(btn => {
        btn.addEventListener("click", ()=>{
            formIsShown = toggleForm(formIsShown);
        });
    }
)


let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide



function loadAllFromOptions(){
    loadOptions("departments");
    loadOptions("jurisdictions");
}


window.onclick = function(event) {
    if (event.target == addToolDiv) {
        addToolDiv.style.display = "none";
    }
}


document.addEventListener("DOMContentLoaded", ()=>{
    loadAllFromOptions();

});


const submitBtn = document.querySelector("#submitBtn");
if (submitBtn) {
    submitBtn.addEventListener("click", function (e) {
        e.preventDefault();
        const name = document.querySelector("#toolName").value;
        const url = document.querySelector("#toolURL").value;
        const tags = document.querySelector("#tags").value.split(",")
            .map(tag => tag.trim())
            .filter(tag => tag !== "");
        let dynamic = document.querySelector('#isDynamic').checked;
        let stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => cb.value);
        const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
        const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);

        const jsonBody = JSON.stringify({name, url, tags, departments, stages, jurisdictions, dynamic});

        MakeToolJSON(jsonBody);

        setTimeout(() => {
            window.location.reload();
        }, 100);
    });
}


