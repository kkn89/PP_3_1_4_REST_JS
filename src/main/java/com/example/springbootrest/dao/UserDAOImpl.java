package com.example.springbootrest.dao;

import com.example.springbootrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@ComponentScan("config")
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Override
    public User getUserById(long id){
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser (User user){
        user.setPassword(bCrypt().encode(user.getPassword()));
        entityManager.persist(user);
        entityManager.close();
    }

    @Override
    public void updateUser(User updatePerson) {
        entityManager.merge(updatePerson);
    }

    @Override
    public void removeUser (long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User getUserByName(String username) {
        return (User) entityManager.createQuery("from User u where u.username=:username")
                .setParameter("username", username).getSingleResult();
    }

}
