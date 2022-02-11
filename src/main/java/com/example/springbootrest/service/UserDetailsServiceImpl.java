package com.example.springbootrest.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
        UserDetails user = null;
        try {
            user = userService.getUserByName(firstName);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with name: %s not found!", firstName));
        }
        return user;
    }
}
