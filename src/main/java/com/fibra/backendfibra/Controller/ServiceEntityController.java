package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Service.ServiceEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceEntityController {

    private final ServiceEntityService service;

    public ServiceEntityController(ServiceEntityService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ServiceEntity create(@RequestBody ServiceEntity serviceEntity) {
        return service.save(serviceEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
