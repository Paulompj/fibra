package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.CustomerWithAppointmentsDTO;
import com.fibra.backendfibra.DTO.CustomersWithAppointmentsResponseDTO;
import com.fibra.backendfibra.DTO.CustomerUpdateRequest;
import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Repository.*;
import com.fibra.backendfibra.Service.CustomerService;
import com.fibra.backendfibra.Service.CustomerTypeService;
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
    private final CustomerTypeService customerTypeService;
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final ExpedientRepository expedientRepository;
    private final DayOffRepository dayOffRepository;
    private final TimeOffRepository timeOffRepository;
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    public CustomerController(UserService userService, CustomerTypeService customerTypeService, UserRepository userRepository,
                              UserServiceRepository userServiceRepository,
                              ExpedientRepository expedientRepository,
                              DayOffRepository dayOffRepository,
                              TimeOffRepository timeOffRepository,
                              CustomerRepository customerRepository,
                              AppointmentRepository appointmentRepository) {
        this.userService = userService;
        this.customerTypeService = customerTypeService;
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
        // Buscar o CustomerType pelo ID fornecido
        if (customer.getCustomerType() == null || customer.getCustomerType().getId() == null) {
            return ResponseEntity.badRequest().body(null); // Retorna erro caso customerType não seja identificado
        }

        CustomerType customerType = customerTypeService.findById(Long.parseLong(String.valueOf(customer.getCustomerType().getId())))
                .orElseThrow(() -> new RuntimeException(
                        "Tipo de cliente não encontrado com o ID: " + customer.getCustomerType().getId()));

        // Associar o CustomerType ao Customer
        customer.setCustomerType(customerType);

        // Salvar o Customer no banco
        Customer savedCustomer = customerService.save(customer);

        return ResponseEntity.ok(savedCustomer);
    }


    @GetMapping
    public ResponseEntity<?> getCustomers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if ((search == null || search.isEmpty()) && (page == null || size == null)) {
            return ResponseEntity.badRequest().body("É obrigatório informar o método de busca");
        }
        if (search != null && !search.isEmpty()) {
            List<Customer> customers = customerService.findCustomersByName(search);
            List<CustomerWithAppointmentsDTO> dtos = customers.stream()
                .map(customer -> new CustomerWithAppointmentsDTO(customer))
                .toList();
            return ResponseEntity.ok(dtos);
        } else {
            if (page == null || size == null || page < 1) {
                return ResponseEntity.badRequest().body("Parâmetros 'page' e 'size' são obrigatórios e 'page' deve ser maior ou igual a 1.");
            }
            var customerPage = customerService.findAllPaginated(page - 1, size);
            var data = customerPage.getContent().stream()
                .map(CustomerWithAppointmentsDTO::new)
                .toList();
            return ResponseEntity.ok(new CustomersWithAppointmentsResponseDTO(
                    String.valueOf(customerPage.getNumber() + 1),
                    data,
                    customerPage.getSize(),
                    customerPage.getTotalPages(),
                    customerPage.getTotalElements()
            ));
        }
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
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        try {
            Customer updated = customerService.update(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
