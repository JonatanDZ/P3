import {MakeToolJSON} from "./fetchTool.js";


export function submitForm(){

    console.log(formToJSON());
    /*
    MakeToolJSON(formToJSON());


    setTimeout(() => {
        window.location.reload();
    }, 100);
     */

}

export function formToJSON(){
    const name = document.querySelector("#toolName").value;
    const isDynamic = document.querySelector('#isDynamic').checked;
    const url = getURLValue(isDynamic);
    const tags = document.querySelector("#tags").value.split(",")
        .map(tag => tag.trim())
        .filter(tag => tag !== "");
    const stage = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => cb.value); //Vi skal finde objekterne tilknyttet og ikke bare navnene
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);

    return JSON.stringify({name, url, isDynamic ,tags, departments, stage, jurisdictions});

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