import {MakeToolJSON} from "./fetchTool.js";


export function submitForm(){

    MakeToolJSON(formToJSON());

    setTimeout(() => {
        window.location.reload();
    }, 100);

}

export function formToJSON(){
    const name = document.querySelector("#toolName").value;
    const url = document.querySelector("#toolURL").value;
    const tags = document.querySelector("#tags").value.split(",")
        .map(tag => tag.trim())
        .filter(tag => tag !== "");
    let dynamic = document.querySelector('#isDynamic').checked;
    let stages = Array.from(document.querySelectorAll('.stagesChecks:checked')).map(cb => cb.value);
    const departments = Array.from(document.querySelectorAll('.departmentsChecks:checked')).map(cb => cb.value);
    const jurisdictions = Array.from(document.querySelectorAll('.jurisdictionsChecks:checked')).map(cb => cb.value);

    return JSON.stringify({name, url, tags, departments, stages, jurisdictions, dynamic});
}