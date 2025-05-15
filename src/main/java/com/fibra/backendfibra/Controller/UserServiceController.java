package com.fibra.backendfibra.Controller;

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
    public UserService createUserService(@RequestBody UserServiceRequest request) {
        return userServiceService.createUserService(request.getUserId(), request.getServiceId());
    }


    // Classe DTO interna para o request
    public static class UserServiceRequest {
        private Long userId;
        private Long serviceId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
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

}
