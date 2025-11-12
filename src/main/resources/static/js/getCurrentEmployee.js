export async function getCurrentEmployee(initials = "PEDO") {
    const response = await fetch(`/employee/initials/${initials}`);
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }
    return await response.json();
}