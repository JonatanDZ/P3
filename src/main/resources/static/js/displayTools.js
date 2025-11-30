import {isToolInFavorite} from "./isToolInFavorite.js";
import {displayFavorites} from "./displayFavorites.js";
import {getToolsDisplay} from "./endpointScripts.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {showTagsInDiv} from "./searchbar.js";

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
        const employee = await getCurrentEmployee(); 
    //has to be for loop, else the async function later will not work
    for (const tool of data) {
        console.log('Tool:', tool.name, 'tags:', tool.tags);

        if (tool.is_dynamic){
            tool.url = tool.url.replace('$USER$', employee.initials.toLowerCase());
        }

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
        //console.log("tjek her", isFav, toolId);
        if(isFav){
            star.textContent = '★';
        } else{
            star.textContent = '☆';

        }
        starBtn.appendChild(star)
        starClicked(starBtn, star, toolId);

        header.appendChild(starBtn);
        header.appendChild(nameE);

        let tags = document.createElement('div');
        tags.className = 'tagsInTool';
        if (!tool.is_personal){

            tags = showTagsInDiv(tool.tags, tags);
        }

/*
        if(Array.isArray(tool.tags)) { 
            for (const tagValue of tool.tags) {
                const tag = document.createElement('span')
                tag.className = 'tag';
                tag.textContent = tagValue;
                tags.appendChild(tag);
            }
        }
*/


        const urlE = document.createElement('div');
        urlE.className = 'tool-url';
        urlE.textContent = tool.url;

        a.appendChild(header);
        a.appendChild(tags);
        a.appendChild(urlE);

        li.appendChild(a);
        list.appendChild(li);

    }
}


