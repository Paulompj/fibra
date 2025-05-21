package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
