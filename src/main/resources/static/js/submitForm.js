import {poster} from "./fetchTool.js";
import {addTagChip} from "./loadOptions.js";


export async function submitTag(){
    try {
        const tagValue = document.querySelector("#tags").value;
        let tag = {value: tagValue};

        tag = await poster("tags", JSON.stringify(tag));

        console.log(tag);
        addTagChip(tag);
    } catch (error) {
        console.error('Error in submitTag:', error);
    }

}

export async function submitForm() {
    try {
        const jsonData = await formToJSON();

        await poster("tools" , jsonData);

    } catch (error) {
        console.log("Error in submitForm:",error);
    }


    //Makes sure tool can be loaded to database before displaying
    setTimeout(() => {
        window.location.reload();
    }, 100);

}

//Makes form data into a JSON
export async function formToJSON(){
    const isPersonal = document.querySelector("#isPersonal").checked;
    const name = document.querySelector("#toolName").value;
    const isDynamic = document.querySelector('#isDynamic').checked;
    const url = getURLValue(isDynamic);
/* //FOR ITERATION 3 - DO NOT REMOVE
    const tags = document.querySelector("#tags").value.split(",")
        .map(tag => tag.trim())
        .filter(tag => tag !== "")
        .map(tag => ({value: tag.value}));

 */
    const stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => ({ id: cb.value }));
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => ({id: cb.value}));
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => ({id: cb.value}));

    return JSON.stringify({is_personal : isPersonal , name, url, is_dynamic : isDynamic, departments, stages, jurisdictions});//, tag});

}

//Gets the url value
function getURLValue(dynamic){
    const url1 = document.querySelector("#toolURL1").value.toString();
    //Combines the two parts of dynamic url and user initials
    if (dynamic){
        const user = document.querySelector("#toolUser").textContent;
        const url2 = document.querySelector("#toolURL2").value.toString();

        return url1 + user + url2;
    } else {
        return url1;
    }
}