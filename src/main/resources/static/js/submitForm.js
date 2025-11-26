import {poster} from "./fetchTool.js";
import {addTagChip} from "./loadOptions.js";

export async function submitTag(){
    
        let input = document.querySelector("#tags");
        let tag = {value: input.value.trim()};

        //Validation for empty tag
        if(input.value.trim() === ""){throw new Error("Tag value cannot be empty");}

        tag = await poster("tags", JSON.stringify(tag));

        addTagChip(tag);
        input.value = "";   
        input.focus();
}

export async function submitForm() {
    try {
        const jsonData = await formToJSON();

        await poster("tools" , jsonData);

        setTimeout(() => {
        window.location.reload();
        }, 100);
    } catch (error) {
        console.log("Error in submitForm:",error);
        alert("Error submitting form: " + error.message);
    }

    //Makes sure tool can be loaded to database before displaying
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
    //Added to the pending tool list
    const pending = !isPersonal;

    //Validation checks

    if (name.trim() === ""){ throw new Error("Tool name cannot be empty"); }

    if (departments.length === 0 && !isPersonal){
         throw new Error("At least one department must be selected if the tool is not only for you.");
    }

    if (stages.length === 0){throw new Error("At least one stage must be selected.");}

    if (jurisdictions.length === 0){throw new Error("At least one jurisdiction must be selected.");}


    return JSON.stringify({is_personal : isPersonal , name, url, is_dynamic : isDynamic, departments, stages, jurisdictions, tags, pending});
}

//Gets the url value
function getURLValue(dynamic){
    const url1 = document.querySelector("#toolURL1").value.toString();

    //Fails if mainUrl input is empty and url is not dynamic
    if(url1.trim() === "" && !dynamic){ throw new Error("URL cannot be empty"); }

    //Combines the two parts of dynamic url and user initials
    if (dynamic){
        const user = document.querySelector("#toolUser").textContent;
        const url2 = document.querySelector("#toolURL2").value.toString();

        //Error handling for empty url
        if (url1.trim() === "" && url2.trim() === ""){ throw new Error("Dynamic URL part cannot be empty"); }

        return url1 + user + url2;
    } else {
        return url1;
    }
}