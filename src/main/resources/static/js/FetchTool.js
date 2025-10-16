// Get the input from the form
export async function MakeToolJSON(toolName, toolURL, Tags, department, stages, isDynamic, jurisdiction) {
    try{

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
