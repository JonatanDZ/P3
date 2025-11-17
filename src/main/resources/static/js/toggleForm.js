//Toggles if the formular is open or not
export function toggleForm(formIsShown){
    if (formIsShown){
        addToolDiv.style.display = "none";
        formIsShown = false;
    } else{
        addToolDiv.style.display = "block";
        formIsShown = true;
    }
    return formIsShown
}

export function toggleCards(i, currentCard){
    //We do this so that you can frontwards again even after you have been at the end
    document.querySelector("#prev").disabled = false;
    document.querySelector("#next").disabled = false;


    const cardValue = parseInt(currentCard) + parseInt(i)
    if (cardValue <= 1) {
        document.querySelector("#prev").disabled = true;

    } else if(cardValue >= 6) {
        document.querySelector("#next").disabled = true;
    }

    document.querySelectorAll(".formCards").forEach( card => {
        card.style.display = "none";
    });
    
    document.querySelector(`#formCard${cardValue}`).style.display = "block";


    return cardValue;
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

    if (document.querySelector("#isDynamic").checked){
        toolURL = document.querySelector("#toolURL1").value + document.querySelector("#toolURL2").value;
    } else {
        toolURL = document.querySelector("#toolURL1").value;
    }
    let jurisdictionString = "";
    let stagesString = "";
    let departmentString = "";
    let tags = "";

    document.querySelectorAll('.jurisdictionsChecks:checked').forEach(cb => {
        jurisdictionString += cb.textContent + ", ";
    });

    document.querySelectorAll('.stagesChecks:checked').forEach(cb => {
        stagesString += cb.name + ", ";
    });


    document.querySelectorAll('.departmentsChecks:checked').forEach(cb => {
        departmentString += cb.textContent + ", ";
    });

    document.querySelectorAll('.tag-chip').forEach(cb => {
        tags += cb.textContent + ", ";
    });

    const para = document.querySelector("#toolToPost");
    para.innerHTML = `
    <div class="tool-summary">
        <h3>${toolName}</h3>
        <p><strong>Departments:</strong> ${departmentString}</p>
        <p><strong>Jurisdictions:</strong> ${jurisdictionString}</p>
        <p><strong>Stages:</strong> ${stagesString}</p>
        <p><strong>URL:</strong> <a href="${toolURL}" target="_blank">${toolURL}</a></p>
        <p><strong>Tags: </strong> ${tags}</p>
    </div>
    `;
}