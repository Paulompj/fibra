package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import com.fibra.backendfibra.Repository.UserRepository;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceService {

    @Autowired
    private UserServiceRepository userServiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceEntityRepository serviceRepository;

    public UserService createUserService(Long userId, Long serviceId) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        ServiceEntity service = serviceRepository.findById(Math.toIntExact(serviceId)).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        UserService userService = new UserService(user, service);
        return userServiceRepository.save(userService);
    }
    public List<UserService> findAll() {
        return userServiceRepository.findAll();
    }

}
