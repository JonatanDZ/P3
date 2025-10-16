// Get the input from the form
export async function MakeToolJSON(name, url, tags, departments, stages, jurisdictions, dynamic) {
    try{
        const response = await fetch("/addTool", {
            method: 'POST',
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({name, url, tags, departments, stages, jurisdictions, dynamic }),

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
