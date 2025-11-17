import {getCurrentEmployee} from "./getCurrentEmployee.js";

export function checkEmployeeDepartment() {
    document.addEventListener("DOMContentLoaded", async () => {
        const employee = await getCurrentEmployee();
        const employeeDepartment = employee.department_name;

        const departmentSelector = document.querySelector(".departmentSelector");
        const departmentRadios = departmentSelector.querySelectorAll('input[type="radio"]');

        console.log(departmentRadios);

        // fejler fordi den ik ser radio buttons
        departmentRadios.forEach(radio => {
            console.log(radio.value);
            if (radio.value === employeeDepartment) {
                console.log(radio.value);
                radio.checked = true;
            }
        });
    });
}
