package com.fibra.backendfibra.DTO;

import java.time.LocalDateTime;

public class AppointmentListDTO {
    public String id;
    public LocalDateTime dateTime;
    public String status;
    public String observations;
    public CustomerDTO customer;
    public ProfessionalDTO professional;
    public ServiceDTO service;

    public AppointmentListDTO() {} // Construtor padrão

    public static class CustomerDTO {
        public String id;        public String fullName;
        public Integer age;
        public String phone;
        public CustomerTypeDTO customerType;

        public CustomerDTO() {} // Construtor padrão
    }
    public static class CustomerTypeDTO {
        public String id;
        public String name;

        public CustomerTypeDTO() {} // Construtor padrão
    }
    public static class ProfessionalDTO {
        public String id;
        public String fullName;

        public ProfessionalDTO() {} // Construtor padrão
    }
    public static class ServiceDTO {
        public String id;
        public String name;
        public Integer duration;

        public ServiceDTO() {} // Construtor padrão
    }
}
