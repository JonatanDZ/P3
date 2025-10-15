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
    const department = "DEVOPS";
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

    // React to changes in branch (radio group inside .branchSelector)
    const branchContainer = document.querySelector('.branchSelector');
    // avoid crashing if it does not exist
    if (branchContainer) {
        // we want to listen to changes and not reloads, since Chrome does not store state of buttons upon reload
        branchContainer.addEventListener('change', (e) => {
            // checking if the event (a change to a branch button) matches one of the actual branch elements in html
            if (e.target && e.target.matches('input[name="branch"]')) {
                const list = document.getElementById('departmentSelected');
                // checking if the element exists, and looping through each link and removing them before appending to the list again
                // this if condition ensures that the list displayed does not concatenate to the list of links
                if (list) {
                    list.querySelectorAll('li').forEach(li => li.remove());
                }
                getLinksByDepartmentJurisdictionStage();
            }
        });
    }

    // React to changes in jurisdiction (checkbox with id="jurisdiction")
    const jurEl = document.getElementById('jurisdiction');
    if (jurEl) {
        jurEl.addEventListener('change', () => {
            const list = document.getElementById('departmentSelected');
            // checking if the element exists, and looping through each link and removing them before appending to the list again
            // this if condition ensures that the list displayed does not concatenate to the list of links
            if (list) {
                list.querySelectorAll('li').forEach(li => li.remove());
            }
            getLinksByDepartmentJurisdictionStage();
        });
    }
});
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