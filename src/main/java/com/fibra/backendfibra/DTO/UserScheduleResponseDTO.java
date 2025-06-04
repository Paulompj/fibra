package com.fibra.backendfibra.DTO;

import java.util.List;

public class UserScheduleResponseDTO {
    private Long id;
    private String fullName;
    private List<ExpedientDTO> expedient;
    private List<TimeOffDTO> timeOffs;
    private List<DayOffDTO> dayOffs;
    private List<AppointmentDTO> appointments;

    public static class ExpedientDTO {
        public Long id;
        public int weekday;
        public String startTime;
        public String endTime;
    }
    public static class TimeOffDTO {
        public Long id;
        public String startDateTime;
        public String endDateTime;
    }
    public static class DayOffDTO {
        public Long id;
        public String dayOff;
    }
    public static class AppointmentDTO {
        public String id;
        public String dateTime;
    }

    public UserScheduleResponseDTO() {}
    public UserScheduleResponseDTO(Long id, String fullName, List<ExpedientDTO> expedient, List<TimeOffDTO> timeOffs, List<DayOffDTO> dayOffs, List<AppointmentDTO> appointments) {
        this.id = id;
        this.fullName = fullName;
        this.expedient = expedient;
        this.timeOffs = timeOffs;
        this.dayOffs = dayOffs;
        this.appointments = appointments;
    }
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public List<ExpedientDTO> getExpedient() { return expedient; }
    public List<TimeOffDTO> getTimeOffs() { return timeOffs; }
    public List<DayOffDTO> getDayOffs() { return dayOffs; }
    public List<AppointmentDTO> getAppointments() { return appointments; }
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setExpedient(List<ExpedientDTO> expedient) { this.expedient = expedient; }
    public void setTimeOffs(List<TimeOffDTO> timeOffs) { this.timeOffs = timeOffs; }
    public void setDayOffs(List<DayOffDTO> dayOffs) { this.dayOffs = dayOffs; }
    public void setAppointments(List<AppointmentDTO> appointments) { this.appointments = appointments; }
}

