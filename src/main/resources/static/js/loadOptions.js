import {submitTag} from "./submitForm.js";
import {stringToColor} from "./searchbar.js";
import {fuzzySearchTags} from "./fuzzySearch.js";

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
    const tags = await loadTags();
    //search specific tag
    const tagInput = document.querySelector('#tags');
    //Dropdown div with suggestions
    const suggestionBox = document.querySelector('#tagsSuggestions');

    let isCompleteMatch = false; //Used to see  if the input is identical to a tag
    let completeMatch = null; //Holds the complete match tag if there is one
    // Responds to typing by user
    tagInput.addEventListener('input',() => {
        suggestionBox.style.display = "block";
        isCompleteMatch = false;
        const input = tagInput.value.toLowerCase();

        if (input.length === 0) {
            clearDiv(suggestionBox);
            return;
        }

        let matches = [];

        //If the tag value includes what is written in input so far. Push it to the match array;
        tags.forEach(tag => {
            if (fuzzySearchTags(input, tag.value)){
                matches.push(tag);
            }
        });
        

        // Hide suggestion box if no matches found
        if (matches.length === 0){
            clearDiv(suggestionBox);
            createSubmitBtn(suggestionBox, input);
            return;
        }

        clearDiv(suggestionBox);

        matches.forEach(tag => {
            // Create wrapper for each suggestion item
            const wrapper = document.createElement("div");

            wrapper.className = "tag-suggestion-item";

            wrapper.tabIndex = 0; // Make it focusable

            // Create text label for the tag
            const span = document.createElement("span");
            span.textContent = tag.value;

            //wrapper.appendChild(checkbox);
            wrapper.appendChild(span);
            suggestionBox.appendChild(wrapper);

            wrapper.addEventListener("click", () => {
                addTagChip(tag);

                // Clear input field but maintain focus for new searches
                tagInput.value = "";
                tagInput.focus();
                suggestionBox.style.display = "none";

            });
 
            //Checks if it is a complete match with one tool. If no add button and store match
            if(tag.value.toLowerCase().trim() === tagInput.value.toLowerCase().trim()){
                isCompleteMatch = true
                completeMatch = tag;
            }

        })
        //Ensure that it dissapears when there is a match
        if(!isCompleteMatch){
            createSubmitBtn(suggestionBox, input);
        }
    })

    tagInput.addEventListener("keypress", (event) => {
        if (event.key === "Enter") {
            event.preventDefault();
            if (isCompleteMatch){  // If there is a match is creates a chip from the tag
                addTagChip(completeMatch);
            } else { // If no it creates a new tag
                try {
                submitTag();
                } catch (error) {
                    alert(error.message);
                    console.error('Error submitting tag:', error);
                }
            }
        }
    })
}

export function clearDiv(div){
    while (div.firstChild) {
        div.removeChild(div.firstChild);
    }
}   

function createSubmitBtn(parentElement, input){
    const wrapper = document.createElement("div");
    wrapper.className = "tag-suggestion-item submit-tag-item";
    wrapper.tabIndex = 0; // Make it focusable

    const submitBtn = document.createElement("span");
    submitBtn.textContent = `Add Tag: "${input}"`;
    submitBtn.id = "submitTagBtn";

    wrapper.appendChild(submitBtn);

    //Sets it as first child
    parentElement.insertBefore(wrapper, parentElement.firstChild);

    //Ensures that when clicked it submits the tag
    wrapper.addEventListener("click", async () => {
        try {
            await submitTag();
        } catch (error) {
            alert(error.message);
            console.error('Error submitting tag:', error);
        }
        clearDiv(parentElement);
    });
}

export function addTagChip(tag){

    const container = document.querySelector("#selectedTags");
    //Ensures that no more than 5 tags can be added
    const tagCount = container.querySelectorAll(".tag-chip").length;
    if(tagCount >= 5){
        alert("You can only add up to 5 tags.");
        return;
    }

    // Prevent duplicate chips for the same tag
    if (container.querySelector(`div.tag-chip[data-tag="${tag.id}"]`) != null) {
        alert(`${tag.value} has already been added.`);
        return;
    }

    // Create chip container
    const chip = document.createElement("div");
    chip.className = "tag-chip";
    chip.dataset.tag = tag.id;
    chip.dataset.tagName = tag.value;
    chip.style.backgroundColor = stringToColor(tag.value);


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
    });

    // Assemble chip components
    chip.appendChild(label);
    chip.appendChild(removeBtn);
    container.appendChild(chip);
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