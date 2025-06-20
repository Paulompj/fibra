package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.ServiceWithUsersRequest;
import com.fibra.backendfibra.DTO.ServiceWithUsersResponse;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Service.ServiceEntityService;
import com.fibra.backendfibra.Service.UserServiceService;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
@Tag(name = "Serviços", description = "Operações relacionadas a Serviços")
@RestController
@RequestMapping("/services")
public class ServiceEntityController {

    private final ServiceEntityService service;
    private final UserServiceService userServiceService;
    private final UserServiceRepository userServiceRepository;

    public ServiceEntityController(ServiceEntityService service, UserServiceService userServiceService, UserServiceRepository userServiceRepository) {
        this.service = service;
        this.userServiceService = userServiceService;
        this.userServiceRepository = userServiceRepository;
    }

    @GetMapping
    public Map<String, Object> getAllCustom(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        if (page < 1) {
            throw new IllegalArgumentException("O número da página deve ser maior ou igual a 1.");
        }
        List<ServiceEntity> services = service.findAllServices();
        List<ServiceWithUsersResponse> dtos = services.stream().map(s -> {
            ServiceWithUsersResponse dto = new ServiceWithUsersResponse();
            dto.setId(String.valueOf(s.getId()));
            dto.setName(s.getName());
            dto.setDescription(s.getDescription());
            dto.setDuration(s.getDuration());
            List<User> users = userServiceService.getUsersByServiceId(Long.valueOf(s.getId()));
            dto.setProfessionals(users.stream().map(u -> new ServiceWithUsersResponse.ProfessionalDTO(String.valueOf(u.getId()), u.getFullName())).collect(Collectors.toList()));
            return dto;
        }).toList();
        int start = (page - 1) * size;
        int end = Math.min(start + size, dtos.size());
        List<ServiceWithUsersResponse> pagedList = (start < end) ? dtos.subList(start, end) : List.of();
        Map<String, Object> response = new HashMap<>();
        response.put("number", page);
        response.put("data", pagedList);
        response.put("size", size);
        response.put("totalPages", (int) Math.ceil((double) dtos.size() / size));
        response.put("totalElements", dtos.size());
        return response;
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
    @GetMapping("/search")
    public ResponseEntity<List<ServiceEntity>> searchServicesByName(@RequestParam String name) {
        List<ServiceEntity> services = service.findServicesByName(name);
        return ResponseEntity.ok(services);
    }

}
