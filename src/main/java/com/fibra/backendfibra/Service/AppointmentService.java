package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.DTO.AppointmentRequest;
import com.fibra.backendfibra.Model.*;
import com.fibra.backendfibra.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    public Page<Appointment> findAll(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
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


    public Appointment updateAppointment(Integer id, AppointmentRequest request) {
        // Verificar se o agendamento existe
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com o ID: " + id));

        // Atualizar os campos do agendamento com base na requisição
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + request.getCustomerId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + request.getUserId()));

        ServiceEntity service = serviceEntityRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + request.getServiceId()));

        // Atualizando as propriedades do agendamento
        existingAppointment.setCustomer(customer);
        existingAppointment.setDateTime(request.getDateTime());
        existingAppointment.setObservations(request.getObservations());
        existingAppointment.setProfessional(user);
        existingAppointment.setService(service);
        existingAppointment.setStatus(request.getStatus());

        // Salvar o agendamento atualizado no repositório
        return appointmentRepository.save(existingAppointment);
    }

}
