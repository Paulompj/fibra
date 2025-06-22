package com.fibra.backendfibra.DTO;

import com.fibra.backendfibra.Model.Appointment.Status;

import java.time.OffsetDateTime;

public class AppointmentRequest {

    private Long customerId;
    private Long userId;
    private Long serviceId;
    private OffsetDateTime dateTime;
    private String observations;
    private Status status;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return Math.toIntExact(userId);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getServiceId() {
        return Math.toIntExact(serviceId);
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
