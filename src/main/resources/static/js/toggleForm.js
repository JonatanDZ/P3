import {updateAllowedCards} from "./addToolForm.js"

//Toggles if the add formular is open or not
export function toggleForm(formIsShown){
    if (formIsShown){
        window.addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        window.addToolDiv.style.display = "block";
        formIsShown = true;
    }
    return formIsShown
}

export function toggleCards(i, currentCard){
    updateAllowedCards();

    const allowed = window.allowedCards;
    const index = allowed.indexOf(currentCard);
    const nextIndex = index + parseInt(i);

    //We do this so that you can frontwards again even after you have been at the end
    document.querySelector("#prev").disabled = nextIndex <= 0;
    document.querySelector("#next").disabled = nextIndex >= allowedCards.length - 1;

    document.querySelectorAll(".formCards").forEach( card => {
        card.style.display = "none";
    });

    const newCard = allowedCards[nextIndex];
    document.querySelector(`#formCard${newCard}`).style.display = "block";

    return newCard;
}

//Changes the url bar to be dynamic or not
export function displayURLbar(dynamicCheck){
    const toolURLInput1 = document.querySelector("#toolURL1");
    const toolURLInput2 = document.querySelector("#toolURL2");
    const user = document.querySelector("#toolUser");
    if (dynamicCheck){
        toolURLInput2.style.display = "inline-block";
        user.style.display = "inline-block"
        toolURLInput1.placeholder = "Enter first part of URL";
    } else {
        toolURLInput2.style.display = "none";
        user.style.display = "none"
        toolURLInput1.placeholder = "Enter URL";
    }
    console.log(toolURLInput2);
}

export function displayReview(){
    const toolName = document.querySelector("#toolName").value;

    let toolURL = "";
    let jurisdictionString = "";
    let stageString = "";
    let departmentString = "";
    let tagString = "";

    if (document.querySelector("#isDynamic").checked){
        toolURL = document.querySelector("#toolURL1").value + "{initials}" + document.querySelector("#toolURL2").value;
    } else {
        toolURL = document.querySelector("#toolURL1").value;
    }

    //Collects answers as strings for summary
    document.querySelectorAll('.jurisdictionsChecks:checked').forEach(cb => {
        jurisdictionString += cb.textContent + ", ";
    });
    jurisdictionString = jurisdictionString.slice(0, -2);

    document.querySelectorAll('.stagesChecks:checked').forEach(cb => {
        stageString += cb.name + ", ";
    });
    stageString = stageString.slice(0, -2); //The last two chars. In this case ", "

    document.querySelectorAll('.departmentsChecks:checked').forEach(cb => {
        departmentString += cb.textContent + ", ";
    });
    departmentString = departmentString.slice(0, -2); //The last two chars. In this case ", "

    //We have to use "dataset.tag" because a div doesn't have the attribute value
    const tagContainer = document.querySelector("#selectedTags");
    tagContainer.querySelectorAll('.tag-chip').forEach(tag => {
        tagString += tag.dataset.tagName + ", ";
    });
    tagString = tagString.slice(0, -2);  //The last two chars. In this case ", "

    const div = document.querySelector(".tool-summary");

    clearDiv(div);

    const isPersonal = document.querySelector("#isPersonal").checked;
    const isDynamic = document.querySelector("#isDynamic").checked;

    // Prints summary for personal and company tools
    if (isPersonal){
        createParagraph(div, "Personal", isPersonal ? "Yes" : "No");
        createParagraph(div, "Name", toolName);
        createParagraph(div, "URL", toolURL);
        createParagraph(div, "Jurisdiction", jurisdictionString);
        createParagraph(div, "Stage", stageString);
    } else{
        createParagraph(div, "Personal", isPersonal ? "Yes" : "No");
        createParagraph(div, "Name", toolName);
        createParagraph(div, "Dynamic", isDynamic ? "Yes" : "No");
        createParagraph(div, "URL", toolURL);
        createParagraph(div, "Departments", departmentString);
        createParagraph(div, "Jurisdiction", jurisdictionString);
        createParagraph(div, "Stage", stageString);
        createParagraph(div, "Tags", tagString);
    }
}

// Makes the html element
export function createParagraph(parent, title, text) {
    const para = document.createElement("p");
    para.textContent = `${title}: ${text}`;
    parent.appendChild(para);
}

// Clears the html element
export function clearDiv(div){
    while(div.lastChild){
        div.removeChild(div.lastChild);    
    }
}