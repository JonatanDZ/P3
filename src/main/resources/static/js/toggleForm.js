
export function toggleForm(formIsShown){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
    return formIsShown
}