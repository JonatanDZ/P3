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
        const tag = await response.json();
        console.log("success:", tag);
        return tag;
    } catch (error) {
        console.error('Error posting tag:', error);
        throw error;
    }
}

