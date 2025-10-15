// getTools endpoint and display
function getToolsDisplay () {
    fetch('/getTools')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('allTools');
            data.forEach(tool => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = tool.url;
                a.textContent = tool.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching tool:', error));
}

function getDepartmentsDisplay(){
    fetch("departments/getAll")
        .then(response=>response.json())
        .then(data => {
            let departmentSelector = document.querySelector(".departmentSelector");
            console.log(departmentSelector);
            data.forEach(department => {
                const input = document.createElement("input");
                input.type = "radio";
                input.name = "department"
                input.value = department.name.toUpperCase();
                input.id = department.name;

                const label = document.createElement("label");
                //input.setAttribute("for", department.name);
                label.htmlFor = department.name;
                label.textContent = department.name;

                console.log(departmentSelector);
                departmentSelector.appendChild(input);
                departmentSelector.appendChild(label);

            })
        })
        .catch(error => console.error('Error fetching Department:', error));

}

// getToolsByDepartmentJurisdictionStage endpoint and display
function getToolsByDepartmentJurisdictionStage() {
    // mock department, should be based on the user logged in
    const department = getDepartment()//"DEVOPS";
    let jurisdiction = getJurisdiction();
    console.log(jurisdiction);
    const branch = getBranch();
    console.log(branch);
    console.log(department);
    fetch(`/getTools/${encodeURIComponent(department)}/${encodeURIComponent(jurisdiction)}/${encodeURIComponent(branch)}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const list = document.getElementById('departmentSelected');
            data.forEach(tool => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = tool.url;
                a.textContent = tool.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching tool:', error));
}

// functions which run when page loads
window.addEventListener('DOMContentLoaded', () => {
    getToolsDisplay();
    getDepartmentsDisplay();
    getToolsByDepartmentJurisdictionStage(); // initial render

    // React to changes in branch (radio group inside .branchSelector)
    const branchContainer = document.querySelector('.branchSelector');
    // avoid crashing if it does not exist
    if (branchContainer) {
        // we want to listen to changes and not reloads, since Chrome does not store state of buttons upon reload
        branchContainer.addEventListener('change', (e) => {
            // checking if the event (a change to a branch button) matches one of the actual branch elements in html
            if (e.target && e.target.matches('input[name="branch"]')) {
                const list = document.getElementById('departmentSelected');
                // checking if the element exists, and looping through each Tool and removing them before appending to the list again
                // this if condition ensures that the list displayed does not concatenate to the list of Tools
                if (list) {
                    list.querySelectorAll('li').forEach(li => li.remove());
                }
                getToolsByDepartmentJurisdictionStage();
            }
        });
    }

    const departmentContainer = document.querySelector('.departmentSelector');
    // avoid crashing if it does not exist
    if (departmentContainer) {
        // we want to listen to changes and not reloads, since Chrome does not store state of buttons upon reload
        departmentContainer.addEventListener('change', (e) => {
            // checking if the event (a change to a branch button) matches one of the actual branch elements in html
            if (e.target && e.target.matches('input[name="department"]')) {
                const list = document.getElementById('departmentSelected');
                // checking if the element exists, and looping through each Tool and removing them before appending to the list again
                // this if condition ensures that the list displayed does not concatenate to the list of Tools
                if (list) {
                    list.querySelectorAll('li').forEach(li => li.remove());
                }
                getToolsByDepartmentJurisdictionStage();
            }
        });
    }

    // React to changes in jurisdiction (checkbox with id="jurisdiction")
    const jurEl = document.getElementById('jurisdiction');
    if (jurEl) {
        jurEl.addEventListener('change', () => {
            const list = document.getElementById('departmentSelected');
            // checking if the element exists, and looping through each Tool and removing them before appending to the list again
            // this if condition ensures that the list displayed does not concatenate to the list of Tools
            if (list) {
                list.querySelectorAll('li').forEach(li => li.remove());
            }
            getToolsByDepartmentJurisdictionStage();
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