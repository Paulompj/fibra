package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-types")
public class CustomerTypeController {

    @Autowired
    private CustomerTypeService service;

    @GetMapping
    public List<CustomerType> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerType> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerType create(@RequestBody CustomerType customerType) {
        return service.save(customerType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<CustomerType> update(@PathVariable Long id, @RequestBody CustomerType customerType) {
        try {
            CustomerType updated = service.update(id, customerType);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
