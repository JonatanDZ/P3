import {poster} from "./fetchTool.js";
import {addTagChip} from "./loadOptions.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";


export async function submitTag(){
    try {
        let input = document.querySelector("#tags");
        let tag = {value: input.value};


        tag = await poster("tags", JSON.stringify(tag));

        console.log(tag);
        addTagChip(tag);
        input.value = "";
        input.focus();
    } catch (error) {
        console.error('Error in submitTag:', error);
    }

}

export async function submitForm() {
    try {
        const jsonData = await formToJSON();

        console.log(jsonData);

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
    //We have to use "dataset.tag" because a div doesn't have the attribute value
    const tags = Array.from(document.querySelectorAll(".tag-chip")).map(tag => ({id : tag.dataset.tag}));
    const stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => ({ id: cb.value }));
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => ({id: cb.value}));
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => ({id: cb.value}));
    // added to the pending tool list
    const pending = !isPersonal;

    const createdBy = await getCurrentEmployee();


    return JSON.stringify({is_personal : isPersonal , name, url, is_dynamic : isDynamic, departments, stages, jurisdictions, tags, pending, created_by : createdBy});
}

//Gets the url value
function getURLValue(dynamic){
    const url1 = document.querySelector("#toolURL1").value.toString();
    //Combines the two parts of dynamic url and user initials
    if (dynamic){
        const user = `$USER$`;
        const url2 = document.querySelector("#toolURL2").value.toString();

        return url1 + user + url2;
    } else {
        return url1;
    }
}