import {MakeToolJSON} from "./fetchTool.js";


export function submitForm(){

    MakeToolJSON(formToJSON());

    setTimeout(() => {
        window.location.reload();
    }, 100);

}

export function formToJSON(){
    const name = document.querySelector("#toolName").value;
    const dynamic = document.querySelector('#isDynamic').checked;
    const url = getURLValue(dynamic);
    const tags = document.querySelector("#tags").value.split(",")
        .map(tag => tag.trim())
        .filter(tag => tag !== "");
    const stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => cb.value);
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);

    return JSON.stringify({name, url, tags, departments, stages, jurisdictions, dynamic});

}

function getURLValue(dynamic){
    const url1 = document.querySelector("#toolURL1").value.toString();
    const user = document.querySelector("#toolUser").textContent;
    const url2 = document.querySelector("#toolURL2").value.toString();

    if (dynamic){
        return url1 + user + url2;
    } else {
        return url1;
    }
}