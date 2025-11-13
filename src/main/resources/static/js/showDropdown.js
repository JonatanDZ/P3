document.addEventListener("DOMContentLoaded", () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });
});