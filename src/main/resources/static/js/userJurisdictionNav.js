import {getJurisdiction} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";

async function displayUserJurisdictionNav(){
    const jurisdiction = getJurisdiction();
    const employee = getCurrentEmployee();
    const employeeName = employee.name;

    const userJurisdictionNav = document.getElementById("userJurisdictionNav");

    userJurisdictionNav.textContent = `${jurisdiction} - ${employeeName}`;
}