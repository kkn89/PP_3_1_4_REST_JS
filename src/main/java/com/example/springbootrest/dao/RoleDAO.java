package com.example.springbootrest.dao;

import com.example.springbootrest.model.Role;
import javassist.NotFoundException;
import java.util.List;

public interface RoleDAO {

    List<Role> getAllRoles();

    Role getRoleByRole(String roleName) throws NotFoundException;

    Role getRoleById(long id);

    Role getRoleByName(String roleName);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(long id);

}
