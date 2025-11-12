import {displayTools, getJurisdiction, getStage} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";

export async function displayFavorites () {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time..
    const list = document.getElementById("favorites");
    list.innerText = "";

    // first gets employee, then the initials from employee
    let employee = await getCurrentEmployee();
    let employeeInitials = employee.initials;

    let jurisdiction = getJurisdiction();
    let stage = getStage();
    fetch(`/employee/${employeeInitials}/favorites?jurisdiction=${jurisdiction}&stage=${stage}`)
        .then(response => response.json())
        .then(data => {
            displayTools(data, list);
        })
        .catch(error => console.error('Error fetching tool:', error));
}