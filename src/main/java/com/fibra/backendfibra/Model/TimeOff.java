package com.fibra.backendfibra.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_offs")
public class TimeOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "users_services_id")
    private UserService userService;

    public TimeOff() {}

    public TimeOff(LocalDateTime startDateTime, LocalDateTime endDateTime, UserService userService) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.userService = userService;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
