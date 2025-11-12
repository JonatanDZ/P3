import {displayTools, getJurisdiction, getStage} from "./endpointScripts.js";
import {getCurrentEmployee, getCurrentEmployeeInitials} from "./getCurrentEmployee.js";

export function displayFavorites () {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time..
    const list = document.getElementById("favorites");
    list.innerText = "";
    // hard coded as of now
    // change to getEmployee method
    getCurrentEmployeeInitials();
    let employeeInitials = "PEDO";
    let jurisdiction = getJurisdiction();
    let stage = getStage();
    fetch(`/employee/${employeeInitials}/favorites?jurisdiction=${jurisdiction}&stage=${stage}`)
        .then(response => response.json())
        .then(data => {
            displayTools(data, list);
        })
        .catch(error => console.error('Error fetching tool:', error));
}