package com.example.springbootrest.controller;

import com.example.springbootrest.model.User;
import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public PageController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/admin";
    }

    @GetMapping("/user")
    public String showUser(@AuthenticationPrincipal User userModel, Model model) throws NotFoundException {
        User user = userService.getUserByName(userModel.getUsername());
        model.addAttribute("user", user);
        return "/user/userInfo";
    }

    @GetMapping("/user/{id}")
    public String showUserById(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/user/userInfo";
    }
}
