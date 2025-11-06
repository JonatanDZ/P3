import {MakeToolJSON} from "./fetchTool.js";


export function submitForm(){
    
    MakeToolJSON(formToJSON());

    setTimeout(() => {
        window.location.reload();
    }, 100);

}

export function formToJSON(){
    const isPersonal = document.querySelector("#isPersonal").checked;
    const name = document.querySelector("#toolName").value;
    const isDynamic = document.querySelector('#isDynamic').checked;
    const url = getURLValue(isDynamic);
/*
    const tags = document.querySelector("#tags").value.split(",")
        .map(tag => tag.trim())
        .filter(tag => tag !== "")
        .map(tag => ({value: tag.value}));

 */
    const stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => ({ id: cb.value }));
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => ({id: cb.value}));
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => ({id: cb.value}));

    return JSON.stringify({isPersonal, name, url, isDynamic, departments, stages, jurisdictions});//, tag});

}

function getURLValue(dynamic){
    const url1 = document.querySelector("#toolURL1").value.toString();
    if (dynamic){
        const user = document.querySelector("#toolUser").textContent;
        const url2 = document.querySelector("#toolURL2").value.toString();

        return url1 + user + url2;
    } else {
        return url1;
    }
}