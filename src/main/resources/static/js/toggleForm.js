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

    } else if(cardValue >= 4) {
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
