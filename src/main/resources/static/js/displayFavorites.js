import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {getJurisdiction, getStage} from "./endpointScripts.js";
import {displayTools} from "./displayTools.js";

export async function displayFavorites (employee) {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time..
    const list = document.getElementById("favorites");
    list.innerText = "";

    let jurisdiction = getJurisdiction();
    let stage = getStage();
    fetch(`/employee/${employee.initials}/favorites?jurisdiction=${jurisdiction}&stage=${stage}`)
        .then(response => response.json())
        .then(data => {
            displayTools(data, list, employee);
        })
        .catch(error => console.error('Error fetching tool:', error));
}