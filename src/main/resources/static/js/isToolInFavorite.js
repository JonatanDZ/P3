import {getJurisdiction, getStage} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";

//Checks if a tool is favorited
export async function isToolInFavorite(toolId) {
    const employee = await getCurrentEmployee();
    const employeeInitials = employee.initials;
    const jurisdiction = getJurisdiction();
    const stage = getStage();


    try {
        //fetches an employees favorite list
        const response = await fetch(
            `/employee/${employeeInitials}/favorites?jurisdiction=${encodeURIComponent(jurisdiction)}&stage=${encodeURIComponent(stage)}`
        );
        const data = await response.json();
        //if toolId parameter is in the favorites list from the employee, it returns true
        console.log("tjek her", data);
        return data.some(favorite => favorite.id === toolId);
    } catch (error) {
        console.error("Error fetching tool:", error);
        return false;
    }
}
