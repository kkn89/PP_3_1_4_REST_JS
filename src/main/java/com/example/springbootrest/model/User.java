package com.example.springbootrest.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private int age;

    public User() {
    }

    public User(String password, String firstName, String lastName, int age) {
        this.password = password;
        this.username = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User(String password, String firstName, String lastName, int age, Set<Role> roles) {
        this.password = password;
        this.username = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String firstName) {
        this.username = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String rolesToString() {
        Set<Role> rolesSet = new TreeSet<Role>(Comparator.comparing(Role::getRole));
        rolesSet.addAll(getRoles());
        StringBuilder roles = new StringBuilder();
        for (Role role : rolesSet) {
            roles.append(role.getRole().replace("ROLE_", "")).append(" ");
        }
        return roles.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && age == user.age && password.equals(user.password) && username.equals(user.username) && lastName.equals(user.lastName) && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, username, lastName, age, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", First name='" + username + '\'' +
                ", Last name='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", roles=" + roles +
                '}';
    }
}
