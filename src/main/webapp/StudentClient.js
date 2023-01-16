const BASE_URL = 'http://localhost:8080/StudentServer-1.0-SNAPSHOT';
let JWT_TOKEN = '';
const getSchool = async () => {
    if (JWT_TOKEN === '') {
        await getToken();
    }
    fetch(BASE_URL + '/api/school', {
        headers: {
            "Authorization": "Bearer " + JWT_TOKEN // <--- add bearer because its standard
        }
    }).then(response => {
        if (response.status === 200) {
            response.json().then(data => {
                const schoolTable = document.getElementById('schoolTable');
                let tableContent = '';
                for (let schoolClass of data.classes) {
                    tableContent += `<tr><td>${schoolClass.name}</td><td>${schoolClass.students}</td></tr>`
                }
                schoolTable.innerHTML = tableContent;
            });
        }
    });
}
const getToken = async () => {
    const response = await fetch(BASE_URL + '/api/jwt', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: "Schachner"
    });
    const json = await response.json();
    JWT_TOKEN = json.token;
    console.log(JWT_TOKEN);
}