document.addEventListener("DOMContentLoaded", () => {
    const dropdownBtn = document.querySelector(".dropdownbtn");
    const dropdownContent = document.getElementById("dropdownIndividualItem");

    dropdownBtn.addEventListener("click", () => {
        dropdownContent.classList.toggle("show");
    });
});

document.addEventListener("DOMContentLoaded", async () => {
    const pendingToolList = await getDepartmentsPendingTools()

    if (pendingToolList.length < 1) {
        // notify bell
        bellNotifier(pendingToolList.length);

        // make dropdown cards
        createDropdownCards(pendingToolList);
    }

    // after DOMContentLoaded is run. its runs every 60 seconds to check for updates.
    setInterval(async () => {
        if (pendingToolList.length < 1) {
            // notify bell
            bellNotifier(pendingToolList.length);

            // make dropdown cards
            createDropdownCards(pendingToolList);
        }
    }, 6000);
});

// skal laves så den kun giver liste med pending tools
async function getDepartmentsPendingTools(department) {
    const response = await fetch(`/employee/department/{department}`);

    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }

    return await response.json();
}

function bellNotifier(pendingToolListLength) {
    // bases on the pendingToolListLength it should make a bell icon that shows the size of the list
}

function createDropdownCards(pendingToolList) {
    const containerDropdownId = document.getElementById("dropdownIndividualItem");

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