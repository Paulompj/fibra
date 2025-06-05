package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.AppointmentListDTO;
import com.fibra.backendfibra.DTO.AppointmentRequest;
import com.fibra.backendfibra.Model.Appointment;
import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Agendamentos", description = "Operações relacionadas a agendamentos")
@RestController
@RequestMapping("/appointments2")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Lista todos os agendamentos", description = "Retorna uma lista paginada de agendamentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
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
    @Operation(summary = "Busca agendamento por ID", description = "Retorna um agendamento pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Cria um novo agendamento", description = "Cria um novo agendamento com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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

