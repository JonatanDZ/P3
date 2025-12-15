// Get the input from the form
export async function poster(destination, jsonBody) {
    try {
        console.log("POSTER: Starting fetch to", `/${destination}`);
        const response = await fetch(`/${destination}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: jsonBody
        });
        const item = await response.json();
        console.log("success:", item);
        return item;
    } catch (error) {
        console.error('Error posting item:', error);
        throw error;
    }
}