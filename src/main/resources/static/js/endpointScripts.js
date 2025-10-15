// getLinks endpoint and display
function getLinksDisplay () {
    fetch('/getLinks')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('allLinks');
            data.forEach(link => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = link.url;
                a.textContent = link.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching links:', error));
}

// getLinksByDepartmentJurisdictionStage endpoint and display
function getLinksByDepartmentJurisdictionStage() {
    // mock department, should be based on the user logged in
    const department = getDepartment()//"DEVOPS";
    let jurisdiction = getJurisdiction();
    console.log(jurisdiction)
    const branch = getBranch();
    console.log(branch);
    fetch(`/getLinks/${encodeURIComponent(department)}/${encodeURIComponent(jurisdiction)}/${encodeURIComponent(branch)}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const list = document.getElementById('departmentSelected');
            data.forEach(link => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = link.url;
                a.textContent = link.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching links:', error));
}

// functions which run when page loads
window.addEventListener('DOMContentLoaded', () => {
    getLinksDisplay();
    getLinksByDepartmentJurisdictionStage(); // initial render
});

// get button from page
// this functionality reloads the links when pressing the reload button
// this ensures that the links do not concatenate on the page..
const reloadButton = document.getElementById('reload_button');
if (reloadButton) {
    reloadButton.addEventListener('click', (e) => {
        e.preventDefault?.();
        // page reload
        window.location.reload();
    });
}

// helper methods
function getJurisdiction() {
    // jurisdiction is a checkbox
    // It is UK when checked and DK when not checked
    // should get jurisdictions from /getJurisdictions, which should return an array of current jurs..
    // get status from element in html
    let jurElement = document.getElementById("jurisdiction");
    const isChecked = jurElement.checked;
    // add a listener, listening for change,
    return isChecked ? "UK" : "DK";
}

function getBranch() {
    // class branchselector is an array of checkboxes, whose value is the desiree.
    // if a certain checkbox is checked, save the value
    // getting the checkbox class
    const container = document.querySelector('.branchSelector');
    // get the currently selected radio
    return container.querySelector('input[type="radio"]:checked')?.value;
}

function getDepartment(){
    const container = document.querySelector('.departmentSelector');
    // get the currently selected radio
    return container.querySelector('input[type="radio"]:checked')?.value;
}