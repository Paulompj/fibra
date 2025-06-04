package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.Map;
import java.util.HashMap;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

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
