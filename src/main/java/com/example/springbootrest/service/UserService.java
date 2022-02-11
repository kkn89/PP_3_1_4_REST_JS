package com.example.springbootrest.service;

import com.example.springbootrest.model.User;
import javassist.NotFoundException;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(long id);

    void saveUser(User user);

    void removeUser(long id);

    void updateUser(User user);

    User getUserByName(String name) throws NotFoundException;

}
