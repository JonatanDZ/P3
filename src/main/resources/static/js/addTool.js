document.querySelector("#addToolBtn").addEventListener("click", showForm); //Event handler for the button
let addToolDiv = document.querySelector("#addToolDiv"); //The div that we want to show and hide
let formIsShown = false;

let departmentDropdown = document.querySelector("#Department");





function showForm(){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
}