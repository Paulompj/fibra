package com.fibra.backendfibra.DTO;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class DashboardStatsDTO {
    public int totalAppointments;
    public int completedAppointments;
    public int canceledAppointments;
    public int pendingAppointments;
    public int totalClients;
    public int totalServices;
    public int totalProfessionals;
    public Map<String, Integer> appointmentsByStatus;
    public Map<String, Integer> appointmentsByService;
    public Map<String, Integer> appointmentsByProfessional;
    public Map<String, Integer> clientsByType;
    public List<RecentActivityDTO> recentActivity;
    public List<UpcomingAppointmentDTO> upcomingAppointments;
    public List<MonthlyAppointmentDTO> monthlyAppointments;

    public static class RecentActivityDTO {
        public int id;
        public String type;
        public String description;
        public OffsetDateTime timestamp;
        public String user;
    }

    public static class UpcomingAppointmentDTO {
        public int id;
        public String customerName;
        public OffsetDateTime dateTime;
        public String professional;
        public String serviceType;
        public String status;
    }

    public static class MonthlyAppointmentDTO {
        public OffsetDateTime date;
        public int count;
    }
}

