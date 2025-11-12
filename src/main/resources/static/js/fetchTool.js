// Get the input from the form
export async function MakeToolJSON(jsonBody) {
    fetch('tools/addTool', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonBody
    })
        .then(response => response.json())
        .then(data => {
            const state = 'Tool Created';
            console.log("success", data);
        })
        .catch((error) => {
            const state = "Error: " + error;
            console.error(state);
        });
}
