package com.fibra.backendfibra.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "expedients")
public class Expedient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private int weekday;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JsonIgnoreProperties("expedients")
    @JoinColumn(name = "users_services_id")
    private UserService userService;

    public Expedient() {}

    public Expedient(int weekday, LocalTime startTime, LocalTime endTime, UserService userService) {
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userService = userService;
    }

    public Long getId() {
        return id;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
