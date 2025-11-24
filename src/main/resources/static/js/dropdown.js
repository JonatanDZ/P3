import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {getToolsDisplay} from "./endpointScripts.js";

document.addEventListener("DOMContentLoaded", async () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    // Listens after click on the bell icon in the navbar
    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });

    // Toggle to show and hide the bell.
    document.addEventListener("click", (event) => {
        const clickInside = dropdownContent.contains(event.target);
        const clickedButton = dropdownBtn.contains(event.target);
        if (!clickInside && !clickedButton) {
            dropdownContent.classList.remove("show");
        }
    });

    // Loads the dropdown onto the site.
    await reloadDropdown();

    // after DOMContentLoaded is run. its runs every 60 seconds to check for updates. Primarily to see if anyone has made a new tool pending.
    setInterval(async () => {
        await reloadDropdown();
        console.log("Refreshed by interval");
    }, 1000 * 60);

});

export async function reloadDropdown() {
    const pendingToolList = await getDepartmentsPendingTools();

    console.log(pendingToolList);

    // Remove old badge if exists
    const containerBellNotifications = document.getElementById("bellNotifications");
    const oldBadge = containerBellNotifications.querySelector(".notificationBadge");
    // needs if statement because the oldBadge dosnt exist the first time you load the site.
    // == You cant remove on null. Therefore, skip if it dosnt exist.
    if (oldBadge) {
        oldBadge.remove();
    }
    // Remove already exising children in the dropdown.
    const containerDropdownId = document.getElementById("dropdownIndividualItem");
    containerDropdownId.replaceChildren();

    // Calls the bell notifier that adds a notification badge if the pending tool list is above 0.

    const currentEmployee = window.employee || await getCurrentEmployee();
    const employeeInitials = currentEmployee.initials;
    let listLengthNotCreatedBy = 0;
    if (pendingToolList.length > 0) {
        pendingToolList.forEach(tool => {
            if (tool.created_by !== employeeInitials) {
                listLengthNotCreatedBy++;
            }
        });
    }

    if (listLengthNotCreatedBy > 0) {
        // notify bell
        bellNotifier(listLengthNotCreatedBy);
    }

    // make dropdown Tools for the dropdown
    await createDropdownCards(pendingToolList, listLengthNotCreatedBy);
}

async function getDepartmentsPendingTools() {
    // Get department name from the user. so only users in that department gets to decide if they want the tool.
    const employee = window.employee || await getCurrentEmployee();
    const employeeDepartment = employee.department_name;

    // we fetch the pending tool list from the user specific department.
    try {
        const response = await fetch(`/tools/pending/department/${employeeDepartment}`);
        return await response.json();
    } catch (error) {
        console.error('Error fetching tools:', error);
        return null;
    }
}

function bellNotifier(pendingToolListLength) {
    // based on the pendingToolListLength it should make a bell icon that shows the size of the list
    const container = document.getElementById("bellNotifications");

    // Create badge
    const notificationBadge = document.createElement("span");
    notificationBadge.classList.add("notificationBadge");
    // adds the tool list length in the badge.
    notificationBadge.textContent = pendingToolListLength;

    container.appendChild(notificationBadge);
}

async function createDropdownCards(pendingToolList, counter) {
    const containerDropdownId = document.getElementById("dropdownIndividualItem");
    const currentEmployee = window.employee || await getCurrentEmployee();
    const employeeInitials = currentEmployee.initials;

    // if the tool list length is above 0 we iterate through the list and make individual tool cards
    if (counter) {
        pendingToolList.forEach(tool => {
            if (tool.created_by !== employeeInitials) {
                const card = createDropdownCard(tool);
                containerDropdownId.appendChild(card);
            }
        });
        // Else we make a placeholder card with: "No actions needed".
    } else {
        const pendingToolCard = document.createElement("div");
        const link = document.createElement("a");
        link.textContent = "No actions needed";

        pendingToolCard.appendChild(link);
        containerDropdownId.appendChild(pendingToolCard);
    }
}

function createDropdownCard(pendingTool) {
    const pendingToolCard = document.createElement("div");

    const link = document.createElement("a");
    link.href = pendingTool.url;
    link.textContent = pendingTool.name;

    const approveBtn = document.createElement("button");
    approveBtn.id = pendingTool.id;
    approveBtn.dataset.tool = pendingTool.id;
    approveBtn.classList.add("aprbtn");
    approveBtn.textContent = "✅";

    const denyBtn = document.createElement("button");
    denyBtn.id = pendingTool.id;
    denyBtn.dataset.tool = pendingTool.id;
    denyBtn.classList.add("denybtn");
    denyBtn.textContent = "🚫";

    pendingToolCard.appendChild(link);
    pendingToolCard.appendChild(approveBtn);
    pendingToolCard.appendChild(denyBtn);

    return pendingToolCard;
}

