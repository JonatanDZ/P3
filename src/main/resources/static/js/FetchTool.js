// Get the input from the form
async function MakeToolJSON() {
    try{
        /*
        const toolName = document.querySelector('#toolName');
        const toolURL = document.querySelector('#toolURL');
        const isDynamic = document.querySelector('#isDynamic');
        const Tags = document.querySelectorAll('#Tags');
        */
        const toolName = "Swagger";
        const toolURL = "https://swagger.io/";
        const Tags = ["tis", "lort"];
        const department = ["Players"];
        const stages = ["Stage"];
        const isDynamic = true;
        const jurisdiction = ["DK"];

        console.log(JSON.stringify({toolName, toolURL, Tags, department, stages, isDynamic, jurisdiction}));

        const response = await fetch("./addTool", {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({toolName, toolURL, Tags, department, stages, isDynamic, jurisdiction}),

        });

        const data = await response.json();
        if (response.ok){
            alert(data.message);
        } else {
            alert(`Error: ${data.message}`);
        }
    } catch (error) {
        console.log("Error: ", error);
        alert("An error has occurred. Please try again.");
    }
}
