package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.CustomerWithAppointmentsDTO;
import com.fibra.backendfibra.DTO.CustomersWithAppointmentsResponseDTO;
import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Repository.*;
import com.fibra.backendfibra.Service.CustomerService;
import com.fibra.backendfibra.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@Tag(name = "Cliente", description = "Operações relacionadas aos Clientes")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final ExpedientRepository expedientRepository;
    private final DayOffRepository dayOffRepository;
    private final TimeOffRepository timeOffRepository;
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomerController(UserService userService, UserRepository userRepository,
                          UserServiceRepository userServiceRepository,
                          ExpedientRepository expedientRepository,
                          DayOffRepository dayOffRepository,
                          TimeOffRepository timeOffRepository,
                          CustomerRepository customerRepository,
                          AppointmentRepository appointmentRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userServiceRepository = userServiceRepository;
        this.expedientRepository = expedientRepository;
        this.dayOffRepository = dayOffRepository;
        this.timeOffRepository = timeOffRepository;
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
    }
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        Customer saved = customerService.save(customer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public Map<String, Object> getAllCustomers(@PageableDefault Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        if (pageNumber < 1) {
            throw new IllegalArgumentException("O número da página deve ser maior ou igual a 1.");
        }
        Page<Customer> page = customerService.findAll(Pageable.ofSize(pageable.getPageSize()).withPage(pageNumber - 1));
        Map<String, Object> response = new HashMap<>();
        response.put("number", page.getNumber() + 1); // página começa em 1
        response.put("data", page.getContent());
        response.put("size", page.getSize());
        response.put("totalPages", page.getTotalPages());
        response.put("totalElements", page.getTotalElements());
        return response;
    }
    @GetMapping("/customers-with-appointments")
    public ResponseEntity<?> getCustomersWithAppointments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().body("O número da página deve ser maior ou igual a 1.");
        }
        Page<Customer> customerPage = customerRepository.findAll(PageRequest.of(page - 1, size));
        List<CustomerWithAppointmentsDTO> data = customerPage.getContent().stream().map(customer -> {
            int count = appointmentRepository.countByCustomerId(customer.getId());
            CustomerType type = customer.getCustomerType();
            CustomerWithAppointmentsDTO.CustomerTypeDTO typeDTO = new CustomerWithAppointmentsDTO.CustomerTypeDTO(
                    type != null ? String.valueOf(customerPage.getNumber() + 1) : null,
                    type != null ? type.getName() : null
            );
            return new CustomerWithAppointmentsDTO(
                    String.valueOf(customer.getId()),
                    customer.getFullName(),
                    customer.getPhone(),
                    customer.getAge(),
                    customer.getAddress(),
                    customer.getPhotoUrl(),
                    typeDTO,
                    count
            );
        }).toList();
        String number = String.valueOf(customerPage.getNumber() + 1);
        return ResponseEntity.ok(new CustomersWithAppointmentsResponseDTO(
                String.valueOf(customerPage.getNumber() + 1),
                data,
                customerPage.getSize(),
                customerPage.getTotalPages(),
                customerPage.getTotalElements()
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updated = customerService.update(id, customer);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
