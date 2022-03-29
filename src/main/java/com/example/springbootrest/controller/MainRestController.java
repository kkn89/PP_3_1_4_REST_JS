package com.example.springbootrest.controller;

import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api")
public class MainRestController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public MainRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        if (userList != null && !userList.isEmpty()) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/showUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping("/userInfo1")
//    public ResponseEntity<User> showUserInfo(@AuthenticationPrincipal User user) throws NotFoundException {
//        User userById = userService.getUserByName(user.getUsername());
//        return ResponseEntity.ok(userById);
//    }

    @PostMapping("/newUser")
    public ResponseEntity<User> addUser(@RequestBody User user) throws NotFoundException {
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> removeUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/userInfo")
    public ResponseEntity<User> showUserById(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserById(user.getId()));
    }
    @GetMapping("/roles")
    ResponseEntity<List<Role>>getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
    @GetMapping("/roles/{id}")
    ResponseEntity<Role> getRoleById(@PathVariable("id") Long id){
        return new ResponseEntity<>(roleService.getRoleById(id), HttpStatus.OK);
    }
}
