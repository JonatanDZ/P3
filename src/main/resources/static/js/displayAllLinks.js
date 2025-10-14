// Fetch the links from the backend endpoint
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