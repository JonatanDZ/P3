import {isToolInFavorite} from "./isToolInFavorite.js";
import {displayFavorites} from "./displayFavorites.js";
import {getToolsDisplay} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";

function starClicked(starBtn, star, toolId) {
    starBtn.appendChild(star);

    starBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        e.stopPropagation();

        let employee = await getCurrentEmployee();
        let employeeInitials = employee.initials;

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


