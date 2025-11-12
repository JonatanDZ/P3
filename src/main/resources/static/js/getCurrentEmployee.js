// Hardcoded for PEDO, should be changed if we want OAuth or another user to be tested.
export async function getCurrentEmployee(initials = "PEDO") {
    const response = await fetch(`/employee/initials/${initials}`);
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }
    return await response.json();
}