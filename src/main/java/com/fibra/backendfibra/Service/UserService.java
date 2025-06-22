package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.DTO.UserWithServicesDTO;
import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Repository.UserRepository;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;


    public UserService(UserRepository userRepository, UserServiceRepository userServiceRepositoryp) {
        this.userRepository = userRepository;
        this.userServiceRepository = userServiceRepositoryp;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
    public Page<UserWithServicesDTO> findUsersWithServices(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> {
            List<com.fibra.backendfibra.Model.UserService> userServices = userServiceRepository.findByUserId(user.getId().longValue());
            List<UserWithServicesDTO.ServiceDTO> services = userServices.stream()
                    .map(us -> new UserWithServicesDTO.ServiceDTO(
                            String.valueOf(us.getService().getId()),
                            us.getService().getName()
                    ))
                    .collect(Collectors.toList());
            return new UserWithServicesDTO(
                    String.valueOf(user.getId()),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole() != null ? user.getRole().name() : null,
                    services
            );
        });
    }

}
