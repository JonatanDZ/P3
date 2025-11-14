import {getCurrentEmployee} from "./getCurrentEmployee.js";

document.addEventListener("DOMContentLoaded", async () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });

    const pendingToolList = await getDepartmentsPendingTools()

    console.log(pendingToolList);

    if (pendingToolList.length > 0) {
        // notify bell
        bellNotifier(pendingToolList.length);

        // make dropdown cards
        createDropdownCards(pendingToolList);
    }

    // after DOMContentLoaded is run. its runs every 60 seconds to check for updates.
    setInterval(async () => {
        if (pendingToolList.length > 0) {
            // notify bell
            bellNotifier(pendingToolList.length);

            // make dropdown cards
            createDropdownCards(pendingToolList);
        }
    }, 6000);
});

// skal laves så den kun giver liste med pending tools
async function getDepartmentsPendingTools() {
    const employee = await getCurrentEmployee();
    const employeeDepartment = employee.department_name;

    try {
        const response = await fetch(`/tools/pending/department/${employeeDepartment}`);
        return await response.json();
    }
    catch (error) {
        console.error('Error fetching tools:', error);
        return null;
    }
}


function bellNotifier(pendingToolListLength) {
    // bases on the pendingToolListLength it should make a bell icon that shows the size of the list
}

function createDropdownCards(pendingToolList) {
    const containerDropdownId = document.getElementById("dropdownIndividualItem");
    console.log("onrgonrongr"+pendingToolList);

    pendingToolList.forEach(pendingToolList => {
        const card = createDropdownCard(pendingToolList);
        containerDropdownId.appendChild(card);
    });
}

function createDropdownCard(pendingTool) {
    const pendingToolCard = document.createElement("a");
    pendingToolCard.href = pendingTool.url;
    pendingToolCard.classList.add("dropdownItemText");

    const label = document.createElement("span")
    label.classList.add("dropdownItemText");
    label.textContent = pendingTool.text;

    const approveBtn = document.createElement("button");
    approveBtn.classList.add("aprbtn");
    approveBtn.textContent = "✅";

    const denyBtn = document.createElement("button");
    denyBtn.classList.add("denybtn");
    denyBtn.textContent = "🚫";

    pendingToolCard.appendChild(label);
    pendingToolCard.appendChild(approveBtn);
    pendingToolCard.appendChild(denyBtn);

    return pendingToolCard;
}