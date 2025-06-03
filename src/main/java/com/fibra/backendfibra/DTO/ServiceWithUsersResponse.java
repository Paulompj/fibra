package com.fibra.backendfibra.DTO;

import java.util.List;

public class ServiceWithUsersResponse {
    private Integer id;
    private String name;
    private Integer duration;
    private String description;
    private List<ProfessionalDTO> professionals;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<ProfessionalDTO> getProfessionals() { return professionals; }
    public void setProfessionals(List<ProfessionalDTO> professionals) { this.professionals = professionals; }

    public static class ProfessionalDTO {
        private Integer id;
        private String fullName;
        public ProfessionalDTO(Integer id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
    }
}

