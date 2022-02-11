package com.example.springbootrest.service;

import com.example.springbootrest.dao.RoleDAO;
import com.example.springbootrest.model.Role;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {


    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(long id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDAO.getRoleByName(roleName);
    }

    @Override
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDAO.updateRole(role);
    }

    @Override
    public void removeRole(long id) {
        roleDAO.removeRole(id);
    }

    @Override
    public Role getRoleByRole(String roleName) throws NotFoundException {
        return roleDAO.getRoleByRole(roleName);
    }
}
