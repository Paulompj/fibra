package com.fibra.backendfibra.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "day_offs")
public class DayOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dayOff;

    @ManyToOne
    @JoinColumn(name = "users_services_id", nullable = false)
    private UserService userService;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDayOff() {
        return dayOff;
    }

    public void setDayOff(LocalDate dayOff) {
        this.dayOff = dayOff;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
