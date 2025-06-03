package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import com.fibra.backendfibra.Service.UserServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<UserService> getAllUserServices() {
        return userServiceService.findAll();
    }

    @GetMapping("/services/{serviceId}/users")
    public List<User> getUsersByServiceId(@PathVariable Long serviceId) {
        return userServiceService.getUsersByServiceId(serviceId);
    }

}
