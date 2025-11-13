document.addEventListener("DOMContentLoaded", () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });
});

// get deparment notifications
// if user(hardcoded one) match a deparment with notifactions (pending tool).
// then add notifications to bell card

// make approve and deny function
