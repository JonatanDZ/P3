// getTools endpoint and display
import {displayFavorites} from "./displayFavorites.js";
import {displayTools} from "./displayTools.js";
import {toggleDepartment} from "./toggleDepartment.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {displayUserJurisdictionNav} from "./userJurisdictionNav.js";

//Gets all tools and displays them individually
export async function getToolsDisplay(employee) {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time.
    const list = document.getElementById('allTools');
    // clearing before displaying tools in order to avoid appending tools
    list.innerHTML = "";
    fetch('/tools')
        .then(response => response.json())
        .then(tool => {
                console.log(employee);
                displayTools(tool, list, employee);
        })
        .catch(error => console.error('Error fetching tool:', error));
}

export function getDepartmentsDisplay(){
    return fetch("/departments")
        .then(response=>response.json())
        .then(data => {
            let departmentSelector = document.querySelector(".departmentSelector");
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


export function getEmployeeFavoritesByJurisdictionAndStage() {
    return fetch(`/employee/${window.employee.initials}/favorites?jurisdiction=${encodeURIComponent(window.jurisdiction)}&stage=${encodeURIComponent(window.stage)}`)
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error('Error fetching favorite tools for employee:', error);
            return false;
        });
   
    /*
    try {
        //fetches an employees favorite list
        const response = await fetch(
            `/employee/${employeeInitials}/favorites?jurisdiction=${encodeURIComponent(jurisdiction)}&stage=${encodeURIComponent(stage)}`
        );
        const data = await response.json();
        //Re
        return data;
    } catch (error) {
        console.error("Error fetching tool:", error);
        return false;
    }
    */
}

// getToolsByDepartmentJurisdictionStage endpoint and display
export async function getToolsByDepartmentJurisdictionStage(department, jurisdiction, branch, employee) {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time..
    const list = document.getElementById('departmentSelected');
    list.innerText = "";
    // mock department, should be based on the user logged in
    fetch(`/tools/department/${encodeURIComponent(department)}/jurisdiction/${encodeURIComponent(jurisdiction)}/stage/${encodeURIComponent(branch)}`)
        .then(response => response.json())
        .then(data => {
            displayTools(data, list, employee);
        })
        .catch(error => console.error('Error fetching tool:', error));
}

// functions which run when page loads
window.addEventListener('DOMContentLoaded', async () => {
    getToolsDisplay();
    await getDepartmentsDisplay();
    await toggleDepartment();
    // redundant since it gets called inside the method above
    // getToolsByDepartmentJurisdictionStage();
    displayFavorites();// initial render

    // Unified change handler — since display functions reset themselves,
    // we only need to re-run them when filters change.
    const onFiltersChange = () => {
        getToolsByDepartmentJurisdictionStage();
        displayFavorites();
    };

    // Branch (stage) radio group
    const branchContainer = document.querySelector('.branchSelector');
    if (branchContainer) {
        branchContainer.addEventListener('change', (e) => {
            if (e.target && e.target.matches('input[name="branch"]')) {
                onFiltersChange();
            }
        });
    }

    // Department radio group
    const departmentContainer = document.querySelector('.departmentSelector');
    if (departmentContainer) {
        departmentContainer.addEventListener('change', (e) => {
            if (e.target && e.target.matches('input[name="department"]')) {
                onFiltersChange();
            }
        });
    }

    // Jurisdiction checkbox
    const jurEl = document.getElementById('jurisdiction');
    if (jurEl) {
        jurEl.addEventListener('change', onFiltersChange);
    }
});


///////// HELPER METHODS /////////

export function getJurisdiction() {
    // jurisdiction is a checkbox
    // It is UK when checked and DK when not checked
    // should get jurisdictions from /getJurisdictions, which should return an array of current jurs.
    // get status from element in html
    let jurElement = document.getElementById("jurisdiction");
    const isChecked = jurElement.checked;
    // add a listener, listening for change,
    return isChecked ? "UK" : "DK";
}

export function getStage() {
    // class branchselector is an array of checkboxes, whose value is the desiree.
    // if a certain checkbox is checked, save the value
    // getting the checkbox class
    const container = document.querySelector('.branchSelector');
    // get the currently selected radio
    return container.querySelector('input[type="radio"]:checked')?.value;
}

export function getDepartment(){
    const container = document.querySelector('.departmentSelector');
    // get the currently selected radio
    return container.querySelector('input[type="radio"]:checked')?.value;
}



