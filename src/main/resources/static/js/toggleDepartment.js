import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {getToolsByDepartmentJurisdictionStage} from "./endpointScripts.js";

export async function toggleDepartment(currentEmployee) {
    // find each department element
    const departments = document.getElementsByClassName("departmentSelector");
    const departmentSelector = departments[0]; // the <div class="departmentSelector">
    const departmentRadios = departmentSelector.querySelectorAll('input[type="radio"][name="department"]');

    // loop through each department in departments
    for (const departmentRadio of departmentRadios) {
        const departmentName = departmentRadio.value;
        // find the employee's department and check it
        // normalize capitalization to avoid errors
        if (departmentName.toUpperCase() === currentEmployee.department_name.toUpperCase()) {
            departmentRadio.checked = true;

            // update the list of department tools displayed
            getToolsByDepartmentJurisdictionStage();
        }
    }
}