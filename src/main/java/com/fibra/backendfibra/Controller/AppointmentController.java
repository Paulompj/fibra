package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.Appointment;
import com.fibra.backendfibra.Service.AppointmentService;
import com.fibra.backendfibra.DTO.AppointmentRequest;
import com.fibra.backendfibra.DTO.AppointmentListDTO;
import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.ServiceEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/appointments2")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Map<String, Object> getAllAppointments(@PageableDefault Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        if (pageNumber < 1) {
            throw new IllegalArgumentException("O número da página deve ser maior ou igual a 1.");
        }
        Page<Appointment> page = appointmentService.findAll(Pageable.ofSize(pageable.getPageSize()).withPage(pageNumber - 1));
        List<AppointmentListDTO> data = page.getContent().stream().map(appointment -> {
            AppointmentListDTO dto = new AppointmentListDTO();
            dto.id = String.valueOf(appointment.getId());
            dto.dateTime = appointment.getDateTime();
            dto.status = mapStatus(appointment.getStatus());
            dto.observations = appointment.getObservations();
            // Customer
            Customer customer = appointment.getCustomer();
            if (customer != null) {
                AppointmentListDTO.CustomerDTO c = new AppointmentListDTO.CustomerDTO();
                c.id = String.valueOf(customer.getId());
                c.fullName = customer.getFullName();
                c.age = customer.getAge();
                c.phone = customer.getPhone();
                if (customer.getCustomerType() != null) {
                    AppointmentListDTO.CustomerTypeDTO ct = new AppointmentListDTO.CustomerTypeDTO();
                    ct.id = String.valueOf(customer.getCustomerType().getId());
                    ct.name = customer.getCustomerType().getName();
                    c.customerType = ct;
                }
                dto.customer = c;
            }
            // Professional
            User professional = appointment.getProfessional();
            if (professional != null) {
                AppointmentListDTO.ProfessionalDTO p = new AppointmentListDTO.ProfessionalDTO();
                p.id = professional.getId() != null ? professional.getId().toString() : null;
                p.fullName = professional.getFullName();
                dto.professional = p;
            }
            // Service
            ServiceEntity service = appointment.getService();
            if (service != null) {
                AppointmentListDTO.ServiceDTO s = new AppointmentListDTO.ServiceDTO();
                s.id = service.getId() != null ? service.getId().toString() : null;
                s.name = service.getName();
                s.duration = service.getDuration();
                dto.service = s;
            }
            return dto;
        }).toList();
        Map<String, Object> response = new HashMap<>();
        response.put("number", page.getNumber() + 1); // página começa em 1
        response.put("data", data);
        response.put("size", page.getSize());
        response.put("totalPages", page.getTotalPages());
        response.put("totalElements", page.getTotalElements());
        return response;
    }

    private String mapStatus(Appointment.Status status) {
        return status != null ? status.name() : null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequest request) throws InterruptedException {
        Appointment appointment = appointmentService.createAppointment(request);
        return ResponseEntity.ok(appointment);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {
        if (appointmentService.findById(id).isPresent()) {
            appointmentService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ...outros endpoints existentes...
}

