package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    int countByCustomerId(Long customerId);
}
