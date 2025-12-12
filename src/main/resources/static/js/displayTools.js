import {isToolInFavorite} from "./isToolInFavorite.js";
import {displayFavorites} from "./displayFavorites.js";
import {getCurrentEmployee} from "./getCurrentEmployee.js";
import {showTagsInDiv} from "./searchbar.js";

//Favorites button
export function starClicked(starBtn, star, toolId) {
    starBtn.appendChild(star);

    starBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        e.stopPropagation();

        let employee = await getCurrentEmployee();
        let employeeInitials = employee.initials;

        const filled = star.textContent === '★';
        const notFilled = !filled;

        //if it isn't filled and is clicked, it should make the star filled
        star.textContent = notFilled ? '★' : '☆';

        //sends the information to the web-page if the ToolCard is shown in the favorites
        try {
            const res = await fetch(`/employee/${employeeInitials}/favorites/${toolId}`, {
                method: notFilled ? 'POST' : 'DELETE',
                headers: {'Content-Type': 'application/json'},
                credentials: 'same-origin'
            });
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            displayFavorites();
        } catch (err) {
            console.error('Favorite toggle failed:', err);
            star.textContent = filled ? '★' : '☆';
        }
    });
}

export async function displayTools(data, list) {
        const employee = await getCurrentEmployee();
    //has to be for loop, else the async function later will not work
    for (const tool of data) {
        //Personalize the dynamic tools
        console.log('Tool:', tool.name, 'tags:', tool.tags);

        if (tool.is_dynamic){
            tool.url = tool.url.replace('$USER$', employee.initials.toLowerCase());
        }

        const toolId = tool.id;
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = tool.url;
        a.target = "_blank"; //The link opens in a new tab

        const header = document.createElement('div');
        header.className = 'tool-header';

        //Show complete Tool name when hovered
        const tooltip = document.createElement("span");
        tooltip.className = "tooltiptext";
        tooltip.textContent = tool.name
        header.appendChild(tooltip)

        const nameE = document.createElement('div');
        nameE.className = 'tool-name';
        nameE.textContent = tool.name;

        const starBtn = document.createElement('button');
        starBtn.className = 'star-button';
        starBtn.setAttribute('aria-label', 'Toggle favorite');

        //ensures the correct star is show (filled or not), but isn't interactive
        const star = document.createElement('span');
        star.className = 'star';
        const isFav = await isToolInFavorite(toolId);
        if(isFav){
            star.textContent = '★';
        } else{
            star.textContent = '☆';
        }
        starBtn.appendChild(star)
        starClicked(starBtn, star, toolId);

        let tags = document.createElement('div');
        tags.className = 'tagsInTool';
        if (!tool.is_personal){

            tags = showTagsInDiv(tool.tags, tags);
        }

        //Circles that indicating stage and gives them the correct color
        const circles = document.createElement("div");
        circles.className = "circles";
        if(tool.stage!=null) {
            for (let i = 0; i < tool.stage.length; i++) {
                let circle = document.createElement("span")
                circle.className = "circle"
                if (tool.stage[i] == "Staging") {
                    circle.style.background = "var(--stage--)"
                } else if (tool.stage[i] == "Development") {
                    circle.style.background = "var(--dev--)"
                } else if (tool.stage[i] == "Production") {
                    circle.style.background = "var(--prod--)"
                }
                circles.appendChild(circle)
            }
        }

        let footer = document.createElement("div");
        footer.className = "footer"

        header.appendChild(nameE);
        header.appendChild(starBtn);

        const urlE = document.createElement('div');
        urlE.className = 'tool-url';
        urlE.textContent = tool.url;

        footer.appendChild(urlE)
        footer.appendChild(circles)

        //append the remaining element to the tool card
        a.appendChild(header);
        a.appendChild(tags);
        a.appendChild(footer);
        li.appendChild(a);
        list.appendChild(li);
    }
}