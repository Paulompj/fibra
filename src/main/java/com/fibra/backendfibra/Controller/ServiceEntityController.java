package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.ServiceWithUsersRequest;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Service.ServiceEntityService;
import com.fibra.backendfibra.Service.UserServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceEntityController {

    private final ServiceEntityService service;
    private final UserServiceService userServiceService;

    public ServiceEntityController(ServiceEntityService service, UserServiceService userServiceService) {
        this.service = service;
        this.userServiceService = userServiceService;
    }

    @GetMapping
    public Page<ServiceEntity> getAll(Pageable pageable) {
        return service.findAll(pageable);
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

    @PostMapping("/with-users")
    public ResponseEntity<ServiceEntity> createWithUsers(@RequestBody ServiceWithUsersRequest request) {
        ServiceEntity savedService = service.createServiceWithUsers(request);
        return ResponseEntity.ok(savedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
