const BASE_URL = 'http://localhost:8080/StudentServer-1.0-SNAPSHOT';
let JWT_TOKEN = '';
const getSchool = async () => {
    if (JWT_TOKEN === '') {
        await getToken(); // wait for token, if not yet available
    }
    fetch(BASE_URL + '/api/school', {
        headers: {
            "Authorization": "Bearer " + JWT_TOKEN // <--- add bearer because its standard
        }
    }).then(response => {
        if (response.status === 200) {
            response.json().then(data => {
                const schoolTable = document.getElementById('schoolTable'); // get table
                let tableContent = '';
                for (let schoolClass of data.classes) {
                    tableContent += `<tr><td>${schoolClass.name}</td><td>${schoolClass.students}</td></tr>`;
                    // add row to table
                }
                schoolTable.innerHTML = tableContent; // set table content
            });
        }
    });
}
const getToken = async () => {
    // get token from server and await response
    const response = await fetch(BASE_URL + '/api/jwt', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: "Schachner" // <--- send username
    });
    const json = await response.json();
    JWT_TOKEN = json.token;
    console.log(JWT_TOKEN);
}