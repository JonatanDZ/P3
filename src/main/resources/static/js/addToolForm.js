import MakeToolJSON from "./MakeToolJSON.js";

document.querySelector("#addToolBtn").addEventListener("click", showForm); //Event handler for the button
let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide
let formIsShown = false;



function loadAllFromOptions(){
    loadOptions("departments");
    loadOptions("jurisdictions");
}

function loadOptions(str){
    fetch(`${str}/getAll`)
        .then(response=>response.json())
        .then(data => {
            let dropdown = document.querySelector(`#${str}Input`);
            data.forEach(item => {

                const checkBoxDiv = document.createElement("div");
                checkBoxDiv.className = "checkBoxDiv";

                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = `${item.name}Input`;
                input.value = item.name;
                input.textContent = item.name;
                input.className = `${str}Checks`;


                const label = document.createElement("label");
                label.for = input.id;
                label.textContent = item.name;

                dropdown.appendChild(checkBoxDiv);
                checkBoxDiv.appendChild(label);
                checkBoxDiv.appendChild(input);
            })
        })
}


function showForm(){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
}

// When the user clicks anywhere outside of the form, close it
window.onclick = function(event) {
    if (event.target == addToolDiv) {
        addToolDiv.style.display = "none";
    }
}


addEventListener("DOMContentLoaded", loadAllFromOptions);


const addToolBtn = document.querySelector("#addToolBtn");
if (addToolBtn) {
    addToolBtn.addEventListener("submit", function(e){
        e.preventDefault();
        const toolName = document.querySelector("#toolName").value;
        const toolURL = document.querySelector("#toolURL").value;
        const tags = document.querySelector("#tags").value;
        let checkedIsDynamic = document.querySelector('#isDynamic').checked;
        let checkedStages = Array.from(document.querySelector('.stagesChecks:checked')).map(cb => cb.value);
        const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
        const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);
        MakeToolJSON(toolName, toolURL, tags, departments, jurisdictions, checkedIsDynamic);
    });

}

