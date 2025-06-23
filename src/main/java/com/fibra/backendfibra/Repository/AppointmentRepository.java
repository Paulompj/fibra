package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    int countByCustomerId(Long customerId);
    int countByStatus(Appointment.Status status);

    @Query("SELECT a.status, COUNT(a) FROM Appointment a GROUP BY a.status")
    java.util.List<Object[]> countByStatusGroup();

    @Query("SELECT a.service.name, COUNT(a) FROM Appointment a GROUP BY a.service.name")
    java.util.List<Object[]> countByServiceGroup();

    @Query("SELECT a.user.fullName, COUNT(a) FROM Appointment a GROUP BY a.user.fullName")
    java.util.List<Object[]> countByProfessionalGroup();

    @Query("SELECT FUNCTION('DATE', a.dateTime), COUNT(a) FROM Appointment a WHERE a.dateTime >= :start AND a.dateTime <= :end GROUP BY FUNCTION('DATE', a.dateTime)")
    java.util.List<Object[]> countMonthlyAppointments(@Param("start") java.time.OffsetDateTime start, @Param("end") java.time.OffsetDateTime end);

    java.util.List<Appointment> findTop5ByOrderByDateTimeDesc();

    java.util.List<Appointment> findTop4ByDateTimeAfterOrderByDateTimeAsc(java.time.OffsetDateTime now);
}
