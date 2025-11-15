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

export async function enableTagSearch(){
    //Loads tags present in DB
    const testTags = await loadTags();
    //search specific tag
    const tagInput = document.querySelector('#tags');
    //Dropdown div with suggestions
    const suggestionBox = document.querySelector('#tagsSuggestions');

    // Responds to typing by user
    tagInput.addEventListener('input',() => {
        const input = tagInput.value.toLowerCase();

        if (input.length === 0) {
            suggestionBox.style.display = "none";
            return;
        }

        let matches = [];

        //If the tag value includes what is written in input so far. Push it to the match array;
        testTags.forEach(tag => {
            if (tag.value.toLowerCase().includes(input)){
                matches.push(tag);
            }
        });

        //The submitTagBtn should only be pressable if the value is not already an option and not nothing
        document.querySelector("#submitTagBtn").disabled = !(matches.length === 0 && input !== "");

        // Hide suggestion box if no matches found
        if (matches.length === 0){
            suggestionBox.style.display = "none";
            return;
        }

        // Clear previous suggestions
        while (suggestionBox.firstChild) {
            suggestionBox.removeChild(suggestionBox.firstChild);
        }
        // Show suggestion box and create new suggestions
        suggestionBox.style.display = "block";
        matches.forEach(tag => {
            // Create wrapper for each suggestion item
            const wrapper = document.createElement("div");
            wrapper.className = "tag-suggestion-item";


            //The way we are solving the problem we don't need cb maybe we should change it up for <span> or <p> for the sake of simplicity?
            // Create checkbox element
            const checkbox = document.createElement("input");
            checkbox.type = "checkbox";
            checkbox.className = "tag-suggestion-checkbox";
            checkbox.value = tag.id;



            // Create text label for the tag
            const span = document.createElement("span");
            span.textContent = tag.value;

            wrapper.appendChild(checkbox);
            wrapper.appendChild(span);
            suggestionBox.appendChild(wrapper);

            wrapper.addEventListener("click", () => {
                addTagChip(tag);

                /*
                const newState = !checkbox.checked;
                checkbox.checked = newState;
                syncTagListCheckbox(tag, newState);

                if (newState) {
                    addTagChip(tag);
                } else {
                // Giver det mening at bruge uncheck, når vores elementer først bliver genereret efter match??
                    uncheckTag(tag);
                }
                */

                // Clear input field but maintain focus for new searches
                tagInput.value = "";
                tagInput.focus();
                suggestionBox.style.display = "none";

            });
        })
    })
}

//What does this do?
function syncTagListCheckbox(tag, shouldCheck){
    const checks = document.querySelectorAll(".tagChecks"); //this class is not being used

    checks.forEach(cb => {
        const labelText = cb.parentElement.textContent.trim().toLowerCase();
        if (labelText === tag.value.toLowerCase()) {
            cb.checked = shouldCheck;
        }
    });
}

/* function syncTagListCheckbox(tagName, shouldCheck){
    const checks = document.querySelectorAll(".tagChecks");

    checks.forEach(cb => {
        const labelText = cb.parentElement.textContent.trim().toLowerCase();
        if (labelText === tagName.toLowerCase()) {
            cb.checked = shouldCheck;
        }
    });
} */


export function addTagChip(tag){
    const container = document.querySelector("#selectedTags");

    // Prevent duplicate chips for the same tag
    if (container.querySelector(`[data-tag="${tag.value}"]`)) {
        return;
    }

    // Create chip container
    const chip = document.createElement("div");
    chip.className = "tag-chip";
    chip.dataset.tag = tag.id;

    // Create tag label
    const label = document.createElement("span");
    label.textContent = tag.value;

    // Create remove button
    const removeBtn = document.createElement("button");
    removeBtn.type = "button";
    removeBtn.textContent = "x";

    removeBtn.addEventListener("click", (e) => {
        // Prevent event bubbling to avoid triggering other click handlers (VERY IMPORTANT)
        e.stopImmediatePropagation();
        e.preventDefault();
        e.stopPropagation();
        // Remove the chip and uncheck associated checkboxes
        chip.remove();
        uncheckTag(tag);
    })
    // Assemble chip components
    chip.appendChild(label);
    chip.appendChild(removeBtn);
    container.appendChild(chip);
}

//What does it do?
function uncheckTag(tag){
    const checks = document.querySelectorAll(".tagChecks");

    checks.forEach(cb => {
        // Find checkboxes that match the tag name
        const labelText = cb.parentElement.textContent.trim().toLowerCase();
        if (labelText === tag.value.toLowerCase()) {
            cb.checked = false;
        }
    })
}

export async function loadTags(){
    try {
        const response = await fetch(`/tags`);
        return await response.json();
    } catch (error) {
        console.error('Error loading tags:', error);
        throw error;
    }
}