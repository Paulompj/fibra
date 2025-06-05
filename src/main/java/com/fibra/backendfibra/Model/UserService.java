package com.fibra.backendfibra.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_services")
public class UserService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;
    @OneToMany(mappedBy = "userService", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DayOff> dayOffs;
    @OneToMany(mappedBy = "userService", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Expedient> expedients;

    @OneToMany(mappedBy = "userService", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<TimeOff> timeOffs;

    public UserService() {}

    public UserService(User user, ServiceEntity service) {
        this.user = user;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceEntity getServiceEntity() {
        return service;
    }

    public void setServiceEntity(ServiceEntity service) {
        this.service = service;
    }
}
