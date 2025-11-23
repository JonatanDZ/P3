import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {getToolsDisplay, getDepartmentsDisplay, getToolsByDepartmentJurisdictionStage} from "./endpointScripts.js";
import {toggleDepartment} from "./toggleDepartment.js";
import {displayUserJurisdictionNav} from "./userJurisdictionNav.js";
import {displayFavorites} from "./displayFavorites.js";
import {getDepartment, getJurisdiction, getStage} from "./endpointScripts.js";

// functions which run when page loads
window.addEventListener('DOMContentLoaded', async () => {

    // We make all needed endpoint calls and store their results in variables
    window.employee = await getCurrentEmployee();
    window.department = await getDepartment()//"DEVOPS";
    window.jurisdiction = await getJurisdiction();
    window.stage = await getStage();
    /*
    const allDepartments = await 
    const allJurisdictions = await
    */



    getToolsDisplay(window.employee);
    await getDepartmentsDisplay();
    await toggleDepartment(window.employee);
    displayUserJurisdictionNav(window.employee, window.jurisdiction);
    // redundant since it gets called inside the method above
    // getToolsByDepartmentJurisdictionStage();
    displayFavorites(window.employee);// initial render
    getToolsByDepartmentJurisdictionStage(window.department, window.jurisdiction, window.stage, window.employee);


    // Unified change handler — since display functions reset themselves,
    // we only need to re-run them when filters change.
    const onFiltersChange = () => {
        getToolsByDepartmentJurisdictionStage(window.department, window.jurisdiction, window.stage, window.employee);
        displayFavorites(window.employee);
    };

    // stage radio group
    const stageContainer = document.querySelector('.branchSelector');
    if (stageContainer) {
        stageContainer.addEventListener('change', async (e) => {
            if (e.target && e.target.matches('input[name="branch"]')) {
                window.stage = await getStage(); //Get the new  value
                onFiltersChange();
            }
        });
    }

    // Department radio group
    const departmentContainer = document.querySelector('.departmentSelector');
    if (departmentContainer) {
        departmentContainer.addEventListener('change', async (e) => {
            if (e.target && e.target.matches('input[name="department"]')) {
                window.department = await getDepartment(); //Get updated department
                onFiltersChange();
            }
        });
    }

    // Jurisdiction checkbox
    const jurEl = document.getElementById('jurisdiction');
    if (jurEl) {
        jurEl.addEventListener('change', async (e) => {
            window.jurisdiction = await getJurisdiction(); // Get updated jurisdiction
            onFiltersChange();
        });
    }
});

function initializeEventListeners() {
    // event listener for the buttons in the tool dropdown.
    document.addEventListener("click", async (event) => {
    // approve button
    if (event.target.classList.contains("aprbtn")) {
        event.preventDefault();
        event.stopPropagation();
        console.log("approve", event.target.dataset.tool);

        const toolId = event.target.dataset.tool;

        try {
            const response = await fetch(`/tools/pending/${toolId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            // check if request was successful
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            await reloadDropdown();
            console.log("Refreshed after approved tool");

            // refreshing the all tools list, since it has a new tool.
            getToolsDisplay();
            console.log("Refreshed all tools after approval in pending");
            return await response.json();
        } catch (error) {
            console.error('Error updating tool:', error);
            return null;
        }
    }

        // deny
        if (event.target.classList.contains("denybtn")) {
            event.preventDefault();
            event.stopPropagation();
            console.log("deny", event.target.dataset.tool);

            const toolId = event.target.dataset.tool;

            try {
                const response = await fetch(`/tools/pending/${toolId}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                // check if request was successful
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }

                await reloadDropdown();
                console.log("Refreshed after denied tool");
            } catch (error) {
                console.error('Error updating tool:', error);
                return null;
            }
        }
    });
}

