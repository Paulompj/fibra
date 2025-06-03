package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.ServiceWithUsersRequest;
import com.fibra.backendfibra.DTO.ServiceWithUsersResponse;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Service.ServiceEntityService;
import com.fibra.backendfibra.Service.UserServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ServiceWithUsersResponse> getAllCustom(Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        // Ajusta para que a paginação comece em 1
        if (page > 0) page = page - 1;
        Pageable realPageable = PageRequest.of(page, size, pageable.getSort());
        List<ServiceEntity> services = service.findAllServices();
        List<ServiceWithUsersResponse> dtos = services.stream().map(s -> {
            ServiceWithUsersResponse dto = new ServiceWithUsersResponse();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setDescription(s.getDescription());
            dto.setDuration(s.getDuration());
            List<User> users = userServiceService.getUsersByServiceId(Long.valueOf(s.getId()));
            dto.setProfessionals(users.stream().map(u -> new ServiceWithUsersResponse.ProfessionalDTO(u.getId(), u.getFullName())).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
        int start = (int) realPageable.getOffset();
        int end = Math.min((start + realPageable.getPageSize()), dtos.size());
        List<ServiceWithUsersResponse> pagedList = dtos.subList(start, end);
        return new PageImpl<>(pagedList, realPageable, dtos.size());
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
