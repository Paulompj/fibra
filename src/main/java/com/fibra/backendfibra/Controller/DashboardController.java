package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.DashboardStatsDTO;
import com.fibra.backendfibra.Model.Appointment;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Repository.AppointmentRepository;
import com.fibra.backendfibra.Repository.CustomerRepository;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import com.fibra.backendfibra.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ServiceEntityRepository serviceEntityRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/stats")
    public DashboardStatsDTO getStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        // Totais
        dto.totalAppointments = (int) appointmentRepository.count();
        dto.completedAppointments = appointmentRepository.countByStatus(Appointment.Status.COMPLETED);
        dto.canceledAppointments = appointmentRepository.countByStatus(Appointment.Status.CANCELED);
        dto.pendingAppointments = appointmentRepository.countByStatus(Appointment.Status.SCHEDULED) + appointmentRepository.countByStatus(Appointment.Status.DELAYED);
        dto.totalClients = (int) customerRepository.count();
        dto.totalServices = (int) serviceEntityRepository.count();
        dto.totalProfessionals = (int) userRepository.countByRole(User.Role.PROFESSIONAL);

        // Por status
        dto.appointmentsByStatus = new HashMap<>();
        for (Object[] row : appointmentRepository.countByStatusGroup()) {
            dto.appointmentsByStatus.put(row[0] != null ? row[0].toString() : "indefinido", toInt(row[1]));
        }
        // Por serviço
        dto.appointmentsByService = new HashMap<>();
        for (Object[] row : appointmentRepository.countByServiceGroup()) {
            dto.appointmentsByService.put(row[0] != null ? row[0].toString() : "indefinido", toInt(row[1]));
        }
        // Por profissional
        dto.appointmentsByProfessional = new HashMap<>();
        for (Object[] row : appointmentRepository.countByProfessionalGroup()) {
            dto.appointmentsByProfessional.put(row[0] != null ? row[0].toString() : "indefinido", toInt(row[1]));
        }
        // Clientes por tipo
        dto.clientsByType = new HashMap<>();
        for (Object[] row : customerRepository.countByTypeGroup()) {
            dto.clientsByType.put(row[0] != null ? row[0].toString() : "indefinido", toInt(row[1]));
        }
        // Atividades recentes (últimos 5 agendamentos)
        dto.recentActivity = new ArrayList<>();
        int activityId = 1;
        for (var appt : appointmentRepository.findTop5ByOrderByDateTimeDesc()) {
            DashboardStatsDTO.RecentActivityDTO act = new DashboardStatsDTO.RecentActivityDTO();
            act.id = activityId++;
            act.type = "appointment_" + appt.getStatus().name().toLowerCase();
            act.description = "Agendamento de " + appt.getCustomer().getFullName() + " " + appt.getStatus().name().toLowerCase();
            act.timestamp = appt.getDateTime();
            act.user = appt.getProfessional() != null ? appt.getProfessional().getFullName() : "Sistema";
            dto.recentActivity.add(act);
        }
        // Próximos agendamentos (próximos 4)
        dto.upcomingAppointments = new ArrayList<>();
        for (var appt : appointmentRepository.findTop4ByDateTimeAfterOrderByDateTimeAsc(OffsetDateTime.now())) {
            DashboardStatsDTO.UpcomingAppointmentDTO up = new DashboardStatsDTO.UpcomingAppointmentDTO();
            up.id = appt.getId();
            up.customerName = appt.getCustomer().getFullName();
            up.dateTime = appt.getDateTime();
            up.professional = appt.getProfessional() != null ? appt.getProfessional().getFullName() : "";
            up.serviceType = appt.getService().getName();
            up.status = appt.getStatus().name();
            dto.upcomingAppointments.add(up);
        }
        // Agendamentos do mês (por dia)
        OffsetDateTime start = OffsetDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        OffsetDateTime end = OffsetDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        dto.monthlyAppointments = new ArrayList<>();
        for (Object[] row : appointmentRepository.countMonthlyAppointments(start, end)) {
            DashboardStatsDTO.MonthlyAppointmentDTO m = new DashboardStatsDTO.MonthlyAppointmentDTO();
            m.date = toOffsetDateTime(row[0]);
            m.count = toInt(row[1]);
            dto.monthlyAppointments.add(m);
        }
        return dto;
    }

    private int toInt(Object value) {
        if (value == null) return 0;
        if (value instanceof Long) return ((Long) value).intValue();
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) return Integer.parseInt((String) value);
        return 0;
    }

    private OffsetDateTime toOffsetDateTime(Object value) {
        if (value == null) return null;
        if (value instanceof OffsetDateTime) return (OffsetDateTime) value;
        if (value instanceof java.sql.Timestamp) return ((java.sql.Timestamp) value).toInstant().atOffset(OffsetDateTime.now().getOffset());
        if (value instanceof java.sql.Date) return ((java.sql.Date) value).toLocalDate().atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
        if (value instanceof java.util.Date) return ((java.util.Date) value).toInstant().atOffset(OffsetDateTime.now().getOffset());
        if (value instanceof java.time.LocalDate) return ((java.time.LocalDate) value).atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
        return null;
    }
}
