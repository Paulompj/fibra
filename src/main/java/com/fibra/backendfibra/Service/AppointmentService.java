package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.DTO.AppointmentRequest;
import com.fibra.backendfibra.Model.*;
import com.fibra.backendfibra.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final ServiceEntityRepository serviceEntityRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              CustomerRepository customerRepository,
                              ServiceEntityRepository serviceEntityRepository,
                              UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.serviceEntityRepository = serviceEntityRepository;
        this.userRepository = userRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(Integer id) {
        return appointmentRepository.findById(id);
    }

    public Appointment createAppointment(AppointmentRequest request) throws InterruptedException {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ServiceEntity service = serviceEntityRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Appointment appointment = new Appointment(
                customer,
                request.getDateTime(),
                request.getObservations(),
                user,
                service,
                request.getStatus()
        );

        return appointmentRepository.save(appointment);
    }

    public void delete(Integer id) {
        appointmentRepository.deleteById(id);
    }
}
