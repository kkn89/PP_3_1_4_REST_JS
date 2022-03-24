function userInfo() {

    fetch('http://localhost:8080/userInfo')
        .then((res) => res.json())
        .then((user) => {
            let temp = "";
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.roles.map(r => r.role.replace('ROLE_', ''))}</td>
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;
        });
}
//вызываем написанную функцию
userInfo();
