package com.example.springbootrest.DefaultDB;

import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitData {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public InitData(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void insertData() throws NotFoundException {

        roleService.saveRole(new Role("ROLE_ADMIN"));
        roleService.saveRole(new Role("ROLE_USER"));

        //На слаке посоветовали чтобы роли не дублировались загружать роли по имени из репозитория, из них делал новый set и в юзера засовывал
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(roleService.getRoleByRole("ROLE_USER"));
        rolesAdmin.add(roleService.getRoleByRole("ROLE_ADMIN"));
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setLastName("admin");
        admin.setAge(4);
        admin.setRoles(rolesAdmin);
        userService.saveUser(admin);

        //На слаке посоветовали чтобы роли не дублировались загружать роли по имени из репозитория, из них делал новый set и в юзера засовывал
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(roleService.getRoleByRole("ROLE_USER"));
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setLastName("user");
        user.setAge(8);
        user.setRoles(rolesUser);
        userService.saveUser(user);

        //На слаке посоветовали чтобы роли не дублировались загружать роли по имени из репозитория, из них делал новый set и в юзера засовывал
        Set<Role> roles1 = new HashSet<>();
//        roles1.add(roleService.getRoleByRole("ROLE_USER"));
//        roles1.add(roleService.getRoleByRole("ROLE_ADMIN"));
        roles1.add(roleService.getRoleByName("ROLE_USER"));
        roles1.add(roleService.getRoleByName("ROLE_ADMIN"));
        User number1 = new User();
        number1.setUsername("ivan");
        number1.setPassword("ivan");
        number1.setLastName("ivan");
        number1.setAge(77);
        number1.setRoles(roles1);
        userService.saveUser(number1);

        Set<Role> roles2 = new HashSet<>();
        //roles2.add(roleService.getRoleByRole("ROLE_USER"));
        roles2.add(roleService.getRoleByName("ROLE_USER"));
        User number2 = new User();
        number2.setUsername("Misha");
        number2.setPassword("Misha");
        number2.setLastName("Misha");
        number2.setAge(28);
        number2.setRoles(roles2);
        userService.saveUser(number2);

    }
}
