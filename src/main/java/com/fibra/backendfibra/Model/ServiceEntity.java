package com.fibra.backendfibra.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer duration; // minutos
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<UserService> userServices;

    public ServiceEntity() {}
    public ServiceEntity(Integer id, String name, String description, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
