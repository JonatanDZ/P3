//Used to load department and jurisdiction in form
export function loadOptions(str){
    //${str} can ex. be jurisdiction or department.
    fetch(`/${str}`)
        .then(response=>response.json())
        .then(data => {
            let dropdown = document.querySelector(`#${str}Input`);
            data.forEach(item => {

                const checkBoxDiv = document.createElement("div");
                checkBoxDiv.className = "checkBoxDiv";

                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = `${item.name}Input`;
                input.value = item.id
                input.textContent = item.name;
                input.className = `${str}Checks`;

                const label = document.createElement("label");
                label.for = input.id;
                label.textContent = item.name;

                dropdown.appendChild(checkBoxDiv);
                checkBoxDiv.appendChild(label);
                checkBoxDiv.appendChild(input);
            })
        })
}

function enableTagSearch(){
    const testTags = ["Fisse med Hår", "Store Bryster", "Fede Finn og funny boyZ", "Belzebub"]
    const tagInput = document.querySelector('#tags');
    const suggestionBox = document.querySelector('#tagsSuggestions');

    
    tagInput.addEventListener('input',() => {
        const input = tagInput.value.toLowerCase();

        if (input.length === 0) {
            suggestionBox.style.display = "none";
            return;
        }

        const matches = testTags.filter(tag =>
        tag.toLowerCase().includes(input)
        );

        if (matches.length === 0){
            suggestionBox.style.display = "none";
            return;
        }

        while (suggestionBox.firstChild) {
            suggestionBox.removeChild(suggestionBox.firstChild);
        }
        suggestionBox.style.display = "block";
        matches.forEach(tag => {
            const wrapper = document.createElement("div");
            wrapper.className = "tag-suggestion-item";

            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.className = "tag-suggestion-checkbox";

            const span = document.createElement("span");
            span.textContent = tag;

            wrapper.appendChild(checkbox);
            wrapper.appendChild(span);
            suggestionBox.appendChild(wrapper);

            wrapper.addEventListener("click", () => {
                const newState = !checkbox.checked;
                checkbox.checked = newState;
                checkTagCheckbox(tag, newState);

                if (newState) {
                    addTagChip(tag);
                } else {
                    uncheckTag(tag);
                }
                //if (!tagInput.value.toLowerCase().includes(tag.toLowerCase())) {
                    //tagInput.value += tag + ", ";

                tagInput.value = "";
                tagInput.focus();
                suggestionBox.style.display = "none";
            });
        })
    })
}

function checkTagCheckbox(tagName, shouldCheck){
    const checks = document.querySelectorAll('.tagChecks');

    checks.forEach(cb => {
        const label = cb.nextElementSibling.textContent.trim().toLowerCase();
        if (label === tagName.toLowerCase()) {
            cb.checked = shouldCheck;
        }
    })
}

function addTagChip(tagName){
    const container = document.querySelector("#selectedTags");
    if (container.querySelector(`[data-tag="${tagName}"]`)) return;

    const chip = document.createElement("div");
    chip.className = "tag-chip";
    chip.dataset.tag = tagName;

    const label = document.createElement("span");
    label.textContent = tagName;

    const removeBtn = document.createElement("button");
    removeBtn.type = "button";
    removeBtn.textContent = "x";

    removeBtn.addEventListener("click", (e) => {
        e.stopImmediatePropagation();
        e.preventDefault();
        e.stopPropagation();
        chip.remove();
        uncheckTag(tagName);
    })
    chip.appendChild(label);
    chip.appendChild(removeBtn);
    container.appendChild(chip);
}

function uncheckTag(tagName){
    const checks = document.querySelectorAll(".tagChecks");

    checks.forEach(cb => {
        const label = cb.nextSibling.textContent.trim().toLowerCase();
        if (label === tagName.toLowerCase()) {
            cb.checked = false;
        }
    })
}

export function loadTags(){
    fetch(`/tags`)
        .then(response => response.json())
        .then(data => {
            let tagList = document.querySelector(`#tagList`);
            data.forEach((item)=>{


                const tagListElement = document.createElement("li");
                tagListElement.className = "tagListElement";


                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = `${item.value}Input`;
                input.value = item.id
                input.textContent = item.value;
                input.className = `tagChecks`;

                const label = document.createElement("label");
                label.for = input.id;
                label.textContent = item.value;
                tagList.appendChild(tagListElement);
                tagListElement.appendChild(input);
                tagListElement.appendChild(label);
            })

            enableTagSearch()
        })
}

export function loadTagAndCheck(id){
    let tagList = document.querySelector(`#tagList`);
    fetch(`/tags/id/${id}`)
        .then(response => response.json())
        .then(data => {
            data.forEach((item)=>{


                const tagListElement = document.createElement("li");
                tagListElement.className = "tagListElement";


                const input = document.createElement("input");
                input.type = "checkbox";
                input.id = `${item.value}Input`;
                input.value = item.id
                input.textContent = item.value;
                input.className = `tagChecks`;
                input.checked = true;

                const label = document.createElement("label");
                label.for = input.id;
                label.textContent = item.value;
                tagList.appendChild(tagListElement);
                tagListElement.appendChild(input);
                tagListElement.appendChild(label);
            })
        })
}