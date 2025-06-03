package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.UserScheduleDTO;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Repository.UserRepository;
import com.fibra.backendfibra.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.TimeOff;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import com.fibra.backendfibra.Repository.ExpedientRepository;
import com.fibra.backendfibra.Repository.DayOffRepository;
import com.fibra.backendfibra.Repository.TimeOffRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users2")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final ExpedientRepository expedientRepository;
    private final DayOffRepository dayOffRepository;
    private final TimeOffRepository timeOffRepository;

    public UserController(UserService userService, UserRepository userRepository,
                         UserServiceRepository userServiceRepository,
                         ExpedientRepository expedientRepository,
                         DayOffRepository dayOffRepository,
                         TimeOffRepository timeOffRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userServiceRepository = userServiceRepository;
        this.expedientRepository = expedientRepository;
        this.dayOffRepository = dayOffRepository;
        this.timeOffRepository = timeOffRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/schedule")
    public ResponseEntity<UserScheduleDTO> getUserScheduleByUserAndService(@RequestParam Long userId, @RequestParam Long serviceId) {
        var userService = userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
        if (userService == null) {
            return ResponseEntity.notFound().build();
        }
        Long userServiceId = userService.getId();
        var expedients = expedientRepository.findByUserServiceId(userServiceId);
        var dayOffs = dayOffRepository.findByUserServiceId(userServiceId);
        var timeOffs = timeOffRepository.findByUserServiceId(userServiceId);
        // Para compatibilidade com o DTO atual, retorna o primeiro de cada lista (ou null)
        var dto = new UserScheduleDTO(
            dayOffs.isEmpty() ? null : dayOffs.get(0),
            expedients.isEmpty() ? null : expedients.get(0),
            timeOffs.isEmpty() ? null : timeOffs.get(0)
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
