document.querySelector("#addToolBtn").addEventListener("click", showForm); //Event handler for the button
let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide
let formIsShown = false;



function loadFromOptions(){
    fetch("departments/getAll")
        .then(response=>response.json())
        .then(data => {
            let departmentDropdown = document.querySelector("#DepartmentInput");
            data.forEach(department => {
                const option = document.createElement("option");
                option.value = department.name;
                option.textContent = department.name;
                departmentDropdown.appendChild(option);
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