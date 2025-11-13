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
        })
}