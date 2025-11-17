import {getCurrentEmployee} from "./getCurrentEmployee.js";

document.addEventListener("DOMContentLoaded", async () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });

    await reloadDropdown();

    // after DOMContentLoaded is run. its runs every 60 seconds to check for updates.
    setInterval(async () => {
        await reloadDropdown();
        console.log("Refreshed by interval");
    }, 1000 * 60);

});

async function reloadDropdown() {
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
    const containerDropdownId = document.getElementById("dropdownIndividualItem");
    containerDropdownId.replaceChildren();

    if (pendingToolList.length > 0) {
        // notify bell
        bellNotifier(pendingToolList.length);
    }
    // make dropdown cards
    createDropdownCards(pendingToolList);
}

// skal laves så den kun giver liste med pending tools
async function getDepartmentsPendingTools() {
    const employee = await getCurrentEmployee();
    const employeeDepartment = employee.department_name;

    try {
        const response = await fetch(`/tools/pending/department/${employeeDepartment}`);
        return await response.json();
    } catch (error) {
        console.error('Error fetching tools:', error);
        return null;
    }
}

function bellNotifier(pendingToolListLength) {
    // bases on the pendingToolListLength it should make a bell icon that shows the size of the list
    const container = document.getElementById("bellNotifications");

    // Create badge
    const notificationBadge = document.createElement("span");
    notificationBadge.classList.add("notificationBadge");
    notificationBadge.textContent = pendingToolListLength;

    container.appendChild(notificationBadge);
}

function createDropdownCards(pendingToolList) {
    const containerDropdownId = document.getElementById("dropdownIndividualItem");

    if (pendingToolList.length > 0) {
        pendingToolList.forEach(pendingToolList => {
            const card = createDropdownCard(pendingToolList);
            containerDropdownId.appendChild(card);
        });
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

document.addEventListener("click", async (event) => {
    // approve
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

            return await response.json();
        } catch (error) {
            console.error('Error updating tool:', error);
            return null;
        }
    }
});