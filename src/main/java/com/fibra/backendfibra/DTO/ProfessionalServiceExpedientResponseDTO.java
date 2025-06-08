package com.fibra.backendfibra.DTO;

import java.util.List;

public class ProfessionalServiceExpedientResponseDTO {
    public Long id;
    public String fullName;
    public String email;
    public String role;
    public List<ServiceWithExpedientsDTO> services;

    public static class ServiceWithExpedientsDTO {
        public Long id;
        public String name;
        public List<ExpedientDTO> expedients;
    }

    public static class ExpedientDTO {
        public Long id;
        public int weekday;
        public String startTime;
        public String endTime;
    }
}

