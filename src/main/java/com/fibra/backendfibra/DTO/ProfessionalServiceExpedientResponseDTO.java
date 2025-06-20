package com.fibra.backendfibra.DTO;

import java.util.List;

public class ProfessionalServiceExpedientResponseDTO {
    public String id;
    public String fullName;
    public String email;
    public String role;
    public List<ServiceWithExpedientsDTO> services;

    public static class ServiceWithExpedientsDTO {
        public String id;
        public String name;
        public List<ExpedientDTO> expedients;
    }

    public static class ExpedientDTO {
        public String id;
        public int weekday;
        public String startTime;
        public String endTime;
    }
}

