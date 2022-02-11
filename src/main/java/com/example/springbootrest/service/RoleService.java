package com.example.springbootrest.service;

import com.example.springbootrest.model.Role;
import javassist.NotFoundException;
import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleByRole(String roleName) throws NotFoundException;

    Role getRoleById(long id);

    Role getRoleByName(String roleName);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(long id);

}
