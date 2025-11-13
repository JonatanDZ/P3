// getTools endpoint and display
import {displayFavorites} from "./displayFavorites.js";
import {isToolInFavorite} from "./isToolInFavorite.js";

//Gets all tools and displays them individually
function getToolsDisplay () {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time.
    const list = document.getElementById('allTools');
    list.innerText = "";
    fetch('/getTools')
        .then(response => response.json())
        .then(data => {
            displayTools(data, list);
        })
        .catch(error => console.error('Error fetching tool:', error));
}


function getDepartmentsDisplay(){
    fetch("departments/getAll")
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

// getToolsByDepartmentJurisdictionStage endpoint and display
function getToolsByDepartmentJurisdictionStage() {
    // clear the list each time it is called.
    // If this is not implemented it appends to the list each time..
    const list = document.getElementById('departmentSelected');
    list.innerText = "";
    // mock department, should be based on the user logged in
    let department = getDepartment()//"DEVOPS";
    let jurisdiction = getJurisdiction();
    let branch = getStage();

    fetch(`/getTools/${encodeURIComponent(department)}/${encodeURIComponent(jurisdiction)}/${encodeURIComponent(branch)}`)
        .then(response => response.json())
        .then(data => {
            displayTools(data, list);
        })
        .catch(error => console.error('Error fetching tool:', error));
}

// functions which run when page loads
window.addEventListener('DOMContentLoaded', () => {
    getToolsDisplay();
    getDepartmentsDisplay();
    getToolsByDepartmentJurisdictionStage();
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

function starClicked(starBtn, star, toolId) {
    starBtn.appendChild(star);

    starBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        e.stopPropagation();

        let employeeInitials = "PEDO";

        const wasFilled = star.textContent === '★';
        const nowFilled = !wasFilled;

        star.textContent = nowFilled ? '★' : '☆';

        try {
            const res = await fetch(`/employee/${employeeInitials}/favorites/${toolId}`, {
                method: nowFilled ? 'POST' : 'DELETE',
                headers: {'Content-Type': 'application/json'},
                credentials: 'same-origin'
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            displayFavorites();

        } catch (err) {
            console.error('Favorite toggle failed:', err);
            star.textContent = wasFilled ? '☆' : '★';
        }
    });
}

export async function displayTools(data, list) {
    //has to be for loop, else the async function later will not work
    for (const tool of data) {
        const toolId = tool.id;
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = tool.url;
        a.target = "_blank";

        const header = document.createElement('div');
        header.className = 'tool-header';

        const nameE = document.createElement('div');
        nameE.className = 'tool-name';
        nameE.textContent = tool.name;

        const starBtn = document.createElement('button');
        starBtn.className = 'star-button';
        starBtn.setAttribute('aria-label', 'Toggle favorite');

        const star = document.createElement('span');
        star.className = 'star';
        const isFav = await isToolInFavorite(toolId);
        if(isFav){
            star.textContent = '★';
        } else{
            star.textContent = '☆';
        }

        starClicked(starBtn, star, toolId);

        header.appendChild(nameE);
        header.appendChild(starBtn);

        const urlE = document.createElement('div');
        urlE.className = 'tool-url';
        urlE.textContent = tool.url;

        a.appendChild(header);
        a.appendChild(urlE);

        li.appendChild(a);
        list.appendChild(li);

    }
}

//this function is duplicated from displayTools and is only used by "displayFavorites" as all stars has to be marked
export function displayFavoriteTools(data, list) {
    data.forEach(favoriteTool => {
        const toolId = favoriteTool.id;
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = favoriteTool.url;
        a.target = "_blank";

        const header = document.createElement('div');
        header.className = 'tool-header';

        const nameE = document.createElement('div');
        nameE.className = 'tool-name';
        nameE.textContent = favoriteTool.name;

        const starBtn = document.createElement('button');
        starBtn.className = 'star-button';
        starBtn.setAttribute('aria-label', 'Toggle favorite');

        const star = document.createElement('span');
        star.className = 'star';
        star.textContent = '★';

        starClicked(starBtn, star, toolId);

        header.appendChild(nameE);
        header.appendChild(starBtn);

        const urlE = document.createElement('div');
        urlE.className = 'tool-url';
        urlE.textContent = favoriteTool.url;

        a.appendChild(header);
        a.appendChild(urlE);

        li.appendChild(a);
        list.appendChild(li);

    });
}