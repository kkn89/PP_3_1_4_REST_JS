/////////////////Таблица всех юзеров
function getAllUsers() {
    fetch("http://localhost:8080/users")
        //Получили промис в первом .then, который примет json и начнет его переделывать в объект, не известно как долго - поэтому промис(обещание), ну а второй .then нам обработает полученный объект
        .then(res => res.json())
        .then(users => {
            let temp = '';
            users.forEach(function (user) {
                //html код взятый из view
                temp += `
                <tr id="tr${user.id}">
                <td id="id${user.id}">${user.id}</td>
                <td id="username${user.id}">${user.username}</td>
                <td id="lastName${user.id}">${user.lastName}</td>
                <td id="age${user.id}">${user.age}</td>
                <td id="roles${user.id}">${user.roles.map(r => r.role.replace('ROLE_', ''))}</td>
                <td>
                <button class="btn btn-info btn-md" type="button"
                data-toggle="modal" data-target="#modalEdit"
                onclick="openModal(${user.id})">Edit</button></td>
                <td>
                <button class="btn btn-danger btn-md" type="button"
                data-toggle="modal" data-target="#modalDelete"
                onclick="openModal(${user.id})">Delete</button></td>
              </tr>`;
            });
            document.getElementById("allUsersTable").innerHTML = temp;
        });
}
//вызываем написанную функцию
getAllUsers()
///////////динамическое получение ролей///////////
function getAllRoles(){
    fetch("http://localhost:8080/roles")
        .then(res => res.json())
        .then(roles => {
            let temp = '';
            roles.forEach(function (role){
                temp +=`
                <option value ="${role.id}">${role.role.replace('ROLE_', '')}</option>
                `
            });
            document.getElementById("newRole").innerHTML = temp;
            document.getElementById("editRole").innerHTML = temp;
            document.getElementById("delRoles").innerHTML = temp;
        });
}
getAllRoles()
///////////////Модальное окно для редактирования и удаления пользователя

//декларируем функцию openModal, описываем тело функции
function openModal(id) {
    fetch("http://localhost:8080/showUser/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {

            //document объект на view и служит документ для отображения и работы с элементам на view, с нашими id , username, password и тд
            document.getElementById('editId').value = user.id;
            document.getElementById('editUsername').value = user.username;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;
            document.getElementById('editRole').value = user.role;

            document.getElementById('delId').value = user.id;
            document.getElementById('delUsername').value = user.username;
            document.getElementById('delLastName').value = user.lastName;
            document.getElementById('delAge').value = user.age;
        })
    });
}

///////////////////////////// Вертикальна карточка по центру Добавление нового юзера////
document.getElementById("NewUserForm")
    .addEventListener("submit", addNewUser); //Берем элемент document.getElementById со страницы и назначаем на него Обработчик событий ("submit" - название действие для обработки,  addNewUser результат)

function addNewUser(e) { // е - объект-событие
    e.preventDefault();// если событие не обрабатывается явно, его действие по умолчанию не должно выполняться так, как обычно.

    let username = document.getElementById('newUsername').value;
    let lastname = document.getElementById('newLastName').value;
    let age = document.getElementById('newAge').value;
    let password = document.getElementById('newPassword').value;
    let roles = getRoles(Array.from(document.getElementById('newRole').selectedOptions));

    fetch("http://localhost:8080/newUser", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            username: username,
            lastName: lastname,
            age: age,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            document.getElementById("usersTabLink").click();
            getAllUsers();
            document.getElementById("NewUserForm").reset();
        })
}

///////////////////Инфо юзера---------------------------

//декларируем функцию showUserInfo(), описываем тело функции
function showUserInfo() {

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
            document.getElementById("showUserInfo").innerHTML = temp;
        });
}
//вызываем написанную функцию
showUserInfo();

//---------------------------Редактирование юзера---------------------------
document.getElementById("editForm")
    .addEventListener("submit", editUser);

function editUser() {
    let roles = getRoles(Array.from(document.getElementById('editRole').selectedOptions));
    let user = {
        id: document.getElementById('editId').value,
        username: document.getElementById('editUsername').value,
        lastName: document.getElementById('editLastName').value,
        age: document.getElementById('editAge').value,
        password: document.getElementById('editPassword').value,
        roles: roles
    }
    let tr =` <tr id="tr${user.id}">
        <td id="id${user.id}">${user.id}</td>
        <td id="username${user.id}">${user.username}</td>
        <td id="lastName${user.id}">${user.lastName}</td>
        <td id="age${user.id}">${user.age}</td>
        <td id="roles${user.id}">${roles.map(r => r.role)}</td>
        <td>
            <button class="btn btn-info btn-md" type="button"
                    data-toggle="modal" data-target="#modalEdit"
                    onclick="openModal(${user.id})">Edit</button></td>
        <td>
            <button class="btn btn-danger btn-md" type="button"
                    data-toggle="modal" data-target="#modalDelete"
                    onclick="openModal(${user.id})">Delete</button></td>
    </tr>`;
    fetch('http://localhost:8080/update', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    }).then(response => {
        $("#modalEdit .close").click();
        $("#tr" + user.id).replaceWith(tr);
    })
}
//---------------------------Удаление юзера---------------------------
async function deleteUser() {
    let userId = document.getElementById("delId").value;
    await fetch("http://localhost:8080/delete/" + userId, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    $("#modalDelete .close").click();
    $("#tr" + userId).remove();
}


/////////////////////////////Получение ролей///////////////////////////
function getRoles(list) {
    let roles = [];
    list.forEach(o => {
        roles.push({"id": o.value, "role": o.text});
    });
    return roles;
}