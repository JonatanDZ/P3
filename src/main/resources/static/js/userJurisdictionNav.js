export function displayUserJurisdictionNav(employee, jurisdiction){
    // The nav id that needs to be adjusted according to the current employee that is signed in (Hardcoded)
    const userJurisdictionNav = document.getElementById("userJurisdictionNav");
    // Html that is injected in the navbar.
    userJurisdictionNav.textContent = `${jurisdiction} - ${employee.name}`;
}