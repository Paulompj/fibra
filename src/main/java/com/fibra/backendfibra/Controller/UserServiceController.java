package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.UserScheduleResponseDTO;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import com.fibra.backendfibra.Service.UserServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user-services")
public class UserServiceController {

    @Autowired
    private UserServiceService userServiceService;
    private UserServiceRepository userServiceRepository;

    @PostMapping
    public List<UserService> createUserServices(@RequestBody UserServiceRequest request) {
        return userServiceService.createUserServices(request.getUserIds(), request.getServiceId());
    }

    // Classe DTO interna para o request
    public static class UserServiceRequest {
        private List<Long> userIds;
        private Long serviceId;

        public List<Long> getUserIds() {
            return userIds;
        }

        public void setUserIds(List<Long> userIds) {
            this.userIds = userIds;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }
    }

    @GetMapping
    public Map<String, Object> getAllUserServices(@PageableDefault Pageable pageable) {
        Page<UserService> page = userServiceService.findAll(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent());
        response.put("totalElements", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("number", page.getNumber());
        response.put("size", page.getSize());
        return response;
    }
    @GetMapping("/id")
    public Long getUserServiceId(@RequestParam Long userId, @RequestParam Long serviceId) {
        UserService userService = userServiceService.findByUserIdAndServiceId(userId, serviceId);
        if (userService != null) {
            return userService.getId();
        }
        return null;
    }

    @GetMapping("/services/{serviceId}/users")
    public List<User> getUsersByServiceId(@PathVariable Long serviceId) {
        return userServiceService.getUsersByServiceId(serviceId);
    }

    @GetMapping("/schedule")
    public UserScheduleResponseDTO getUserScheduleByUserAndService(@RequestParam Long userId, @RequestParam Long serviceId) {
        return userServiceService.getUserScheduleByUserAndService(userId, serviceId);
    }

}
