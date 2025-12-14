import {getJurisdiction} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";
//Show the current user and which jurisdiction is currently selected

async function displayUserJurisdictionNav(){
    const jurisdiction = getJurisdiction();
    // currently hardcoded in the getCurrentEmployee() function.
    const employee = await getCurrentEmployee();
    const employeeName = employee.name;

    // The nav id that needs to be adjusted according to the current employee that is signed in (Hardcoded)
    const userJurisdictionNav = document.getElementById("userJurisdictionNav");
    // Html that is injected in the navbar.
    userJurisdictionNav.textContent = `${jurisdiction} - ${employeeName}`;
}

// Function call to load when index.html is loaded.
await displayUserJurisdictionNav();