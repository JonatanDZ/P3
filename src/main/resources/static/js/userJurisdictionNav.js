import {getJurisdiction} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";

async function displayUserJurisdictionNav(){
    const jurisdiction = getJurisdiction();
    // currently hardcoded
    const employee = await getCurrentEmployee("PEDO");
    const employeeName = employee.name;

    const userJurisdictionNav = document.getElementById("userJurisdictionNav");

    userJurisdictionNav.textContent = `${jurisdiction} - ${employeeName}`;
}

await displayUserJurisdictionNav();