export async function getCurrentEmployee(initials = "PEDO") {
    // hardcoded by having a constant argument of employee with initials: "PEDO"
    // fetching from employee endpoint. Returns a JSON object of the employee
    const response = await fetch(`/employee/initials/${initials}`);
    // throws if  other response than 200
    if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
    }
    // returns entire json object. It is expected to access attributes by dot notation after getting  the entire user object.
    return await response.json();
}