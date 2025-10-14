// getLinks endpoint and display
function getLinksDisplay () {
    fetch('/getLinks')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('allLinks');
            data.forEach(link => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = link.url;
                a.textContent = link.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching links:', error));
}

// getLinksByDepartmentJurisdictionStage endpoint and display
function getLinksByDepartmentJurisdictionStage() {
    // mock department, should be based on the user logged in
    const department = "DEVOPS";
    const jurisdiction = "UK";
    const branch = "DEVELOPMENT";
    fetch(`/getLinks/${encodeURIComponent(department)}/${encodeURIComponent(jurisdiction)}/${encodeURIComponent(branch)}`)
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('departmentSelected');
            data.forEach(link => {
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = link.url;
                a.textContent = link.name;
                a.target = "_blank";
                li.appendChild(a);
                list.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching links:', error));
}


window.addEventListener("DOMContentLoaded", () => {
    // you already call getLinksDisplay() here
    getLinksDisplay();
    getLinksByDepartmentJurisdictionStage();
})
