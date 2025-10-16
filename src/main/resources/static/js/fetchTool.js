// Get the input from the form
export async function MakeToolJSON(jsonBody) {
    fetch('addTool', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonBody
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
