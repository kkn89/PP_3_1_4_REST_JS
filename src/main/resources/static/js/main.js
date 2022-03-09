/////////////////Таблица всех юзеров

//декларируем функцию getAllUsers, описываем тело функции
function getAllUsers() {
    fetch("http://localhost:8080/api/users")
        //Получили промис в первом .then, который примет json и начнет его переделывать в объект, не известно как долго - поэтому промис(обещание), ну а второй .then нам обработает полученный объект
        .then(res => res.json())
        .then(users => {
            // используем let, var устаревший вариант задания переменных + var видна везде , а let только в блоке функции
            let temp = '';
            users.forEach(function (user) {
                //html код взятый из view
                temp += `
                <tr>
                <td id="id${user.id}">${user.id}</td>
                <td id="username${user.id}">${user.username}</td> 
                <td id="lastName${user.id}">${user.lastName}</td> 
                <td id="age${user.id}">${user.age}</td>
                <td id="roles${user.id}">${user.roles.map(r => r.role.replace('ROLE_', '')).join(', ')}</td>
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

///////////////Модальное окно для редактирования и удаления пользователя

//декларируем функцию openModal, описываем тело функции
function openModal(id) {
    fetch("http://localhost:8080/api/showUser/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {

            //document объект на view и служит документ для отображения и работы с элементам на view, с нашими id , username, password и тд
            document.getElementById('id').value = user.id;
            document.getElementById('editUsername').value = user.username;
            document.getElementById('editPassword').value = user.password;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editAge').value = user.age;

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
    let roles = getRoles(Array.from(document.getElementById('newRole').selectedOptions)
        .map(role => role.value));
    fetch("http://localhost:8080/api/newUser", {
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
    fetch('http://localhost:8080/api/userInfo')
        .then((res) => res.json())
        .then((user) => {
            let temp = "";
            temp += `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.roles.map(r => r.role.replace("ROLE_", "")).join(", ")}</td>
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;
        });
}

//вызываем написанную функцию
showUserInfo();

//---------------------------Редактирование юзера---------------------------
document.getElementById("editForm")
    .addEventListener("submit", editUser);

async function editUser() {
    let user = {
        id: document.getElementById('id').value,
        username: document.getElementById('editUsername').value,
        lastName: document.getElementById('editLastName').value,
        age: document.getElementById('editAge').value,
        password: document.getElementById('editPassword').value,
        roles: $('#editRole').val()
    }
    const updated = await fetch('http://localhost:8080/api/update', {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    }).then(response => console.log(response));

    if (updated) {
        $("#modalEdit .close").click();
        await this.refreshTable();
    }
    // $("#modalEdit .close").click();
    // refreshTable();
}

//---------------------------Удаление юзера---------------------------
async function deleteUser() {
    await fetch("http://localhost:8080/api/delete/" + document.getElementById("delId").value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
    })

    $("#modalDelete .close").click();
    refreshTable();
}

//---------------------------Обновление таблицы юзеров---------------------------
function refreshTable() {
    let table = document.getElementById('allUsersTable')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(getAllUsers, 200);
}

/////////////////////////////Получение ролей///////////////////////////
function getRoles(list) {
    let roles = [];
//Если позиция элемента есть, то есть и эллемент
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    return roles;
}