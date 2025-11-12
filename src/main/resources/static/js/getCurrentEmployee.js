
export function getCurrentEmployee() {
    let initials = "PEDO";
    fetch(`/employee/initials/${initials}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error('Error fetching tool:', error));
}

getCurrentEmployee();