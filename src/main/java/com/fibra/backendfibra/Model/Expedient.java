package com.fibra.backendfibra.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "expedients")
public class Expedient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private int weekday;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @ManyToOne
    @JsonIgnoreProperties("expedients")
    @JoinColumn(name = "users_services_id")
    private UserService userService;

    public Expedient() {}

    public Expedient(int weekday, OffsetDateTime startTime, OffsetDateTime endTime, UserService userService) {
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

    public String getStartTime() {
        return startTime != null ? startTime.toLocalTime().toString() : null;
    }

    public String getEndTime() {
        return endTime != null ? endTime.toLocalTime().toString() : null;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }
}
