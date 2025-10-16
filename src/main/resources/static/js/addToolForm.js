document.querySelector("#addToolBtn").addEventListener("click", showForm); //Event handler for the button
let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide
let formIsShown = false;



function loadFromOptions(){
    fetch("departments/getAll")
        .then(response=>response.json())
        .then(data => {
            let departmentDropdown = document.querySelector("#DepartmentInput");
            data.forEach(department => {

                const checkBoxDiv = document.createElement("div");
                checkBoxDiv.className = "checkBoxDiv";

                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = department.name;
                input.value = department.name;
                input.textContent = department.name;

                const label = document.createElement("label");
                label.for = input.id;
                label.textContent = department.name;

                departmentDropdown.appendChild(checkBoxDiv);
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

addEventListener("DOMContentLoaded", loadFromOptions)