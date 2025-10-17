import {MakeToolJSON} from "./fetchTool.js";

const toggleBtns = document.querySelectorAll(".toggleBtn");

toggleBtns.forEach(btn => {
        btn.addEventListener("click", toggleForm);//Event handler for the button
    }
)


let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide
let formIsShown = false;



function loadAllFromOptions(){
    loadOptions("departments");
    loadOptions("jurisdictions");
}

function loadOptions(str){
    fetch(`/${str}/getAll`)
        .then(response=>response.json())
        .then(data => {
            let dropdown = document.querySelector(`#${str}Input`);
            data.forEach(item => {

                const checkBoxDiv = document.createElement("div");
                checkBoxDiv.className = "checkBoxDiv";

                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = `${item.name}Input`;
                input.value = item.name.toUpperCase();
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


function toggleForm(){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
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
    submitBtn.addEventListener("click", function(e){
        e.preventDefault();
        const name = document.querySelector("#toolName").value;
        const url = document.querySelector("#toolURL").value;
        const tags = document.querySelector("#tags").value.split(",")
                                                                .map(tag=>tag.trim())
                                                                .filter(tag=>tag!== "");
        let dynamic = document.querySelector('#isDynamic').checked;
        let stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => cb.value);
        const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
        const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);

        const jsonBody = JSON.stringify({name, url, tags, departments, stages, jurisdictions, dynamic });

        MakeToolJSON(jsonBody);

        window.location.reload();
    });


}

