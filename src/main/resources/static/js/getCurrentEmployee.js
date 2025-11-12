function getCurrentEmployeeInitials(initials = "PEDO") {
    return fetch(`/employee/initials/${initials}`)
        .then(r => {
            if (!r.ok) throw new Error(`HTTP ${r.status}`);
            return r.json();
        })
        .then(data => data.initials); // ← the attribute
}