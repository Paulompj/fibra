package com.fibra.backendfibra.DTO;

import java.util.List;

public class UserWithServicesResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private List<ServiceDTO> services;

    public UserWithServicesResponseDTO(Long id, String fullName, String email, String role, List<ServiceDTO> services) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.services = services;
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public List<ServiceDTO> getServices() { return services; }

    public static class ServiceDTO {
        private Long id;
        private String name;

        public ServiceDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
    }
}

