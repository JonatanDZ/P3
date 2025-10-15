document.querySelector("#addTool").addEventListener("click", showForm);
let addToolDiv = document.querySelector("#addToolDiv");
let formIsShown = false;

function showForm(){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
}