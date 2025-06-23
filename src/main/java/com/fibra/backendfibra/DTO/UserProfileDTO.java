package com.fibra.backendfibra.DTO;

import com.fibra.backendfibra.Model.User;

public class UserProfileDTO {
    private Integer id;
    private String fullName;
    private String email;
    private User.Role role;

    public UserProfileDTO(Integer id, String fullName, String email, User.Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public User.Role getRole() {
        return role;
    }
}
