package com.fibra.backendfibra.Model;

import jakarta.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users2")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    public User() {}
    public User(Integer id, String fullName, String email, String password, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserService> userServices;

    public List<UserService> getUserServices() {
        return userServices;
    }
    public void setUserServices(List<UserService> userServices) {
        this.userServices = userServices;
    };
    public void addUserService(UserService userService) {
        if (userServices == null) {
            userServices = new ArrayList<>();
        }
        userServices.add(userService);
        userService.setUser(this);
    };
    public void removeUserService(UserService userService) {
        if (userServices != null) {
            userServices.remove(userService);
            userService.setUser(null);
        }
    };
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public enum Role {
        ADMIN, PROFESSIONAL, USER
    }
}
