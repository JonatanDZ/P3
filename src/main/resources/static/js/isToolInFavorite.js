import {getJurisdiction, getStage} from "./endpointScripts.js";

export async function isToolInFavorite(toolId) {
    const employeeInitials = "PEDO";
    const jurisdiction = getJurisdiction();
    const stage = getStage();


    try {
        //fetches an employees favorite list
        const response = await fetch(
            `/employee/${employeeInitials}/favorites?jurisdiction=${encodeURIComponent(jurisdiction)}&stage=${encodeURIComponent(stage)}`
        );
        const data = await response.json();
        //if toolId parameter is in the favorites list from the employee, it returns true
        return data.some(favorite => favorite.id === toolId);
    } catch (error) {
        console.error("Error fetching tool:", error);
        return false;
    }
}
