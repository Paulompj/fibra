package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Repository.ExpedientRepository;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpedientService {

    @Autowired
    private ExpedientRepository expedientRepository;

    @Autowired
    private UserServiceRepository userServiceRepository;

    @Autowired
    private com.fibra.backendfibra.Repository.ServiceEntityRepository serviceEntityRepository;

    @Autowired
    private com.fibra.backendfibra.Repository.UserRepository userRepository;



    public Expedient createExpedient(int weekday, String startTime, String endTime, Long userServiceId) {
        UserService userService = userServiceRepository.findById(userServiceId)
                .orElseThrow(() -> new RuntimeException("UserService não encontrado"));

        Expedient expedient = new Expedient(
                weekday,
                java.time.OffsetDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.parse(startTime), java.time.ZoneOffset.UTC),
                java.time.OffsetDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.parse(endTime), java.time.ZoneOffset.UTC),
                userService
        );

        return expedientRepository.save(expedient);
    }

    public List<Expedient> findAll() {
        return expedientRepository.findAll();
    }

    public List<Expedient> getByUserServiceId(Long userServiceId) {
        return expedientRepository.findByUserServiceId(userServiceId);
    }

    public UserService findUserServiceByUserIdAndServiceId(Long userId, Long serviceId) {
        return userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
    }

    public UserService getOrCreateUserService(Long userId, Long serviceId) {
        UserService userService = userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
        if (userService != null) {
            return userService;
        }
        com.fibra.backendfibra.Model.User user = userRepository.findById(userId.intValue())
                .orElseThrow(() -> new RuntimeException("User não encontrado"));
        ServiceEntity service = serviceEntityRepository.findById(Math.toIntExact(serviceId))
                .orElseThrow(() -> new RuntimeException("ServiceEntity não encontrado"));
        userService = new UserService(user, service);
        return userServiceRepository.save(userService);
    }

    public void deleteExpedient(Long id) {
        expedientRepository.deleteById(id);
    }

    public Expedient updateExpedient(Long id, int weekday, String startTime, String endTime, Long userServiceId) {
        Expedient expedient = expedientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expedient não encontrado"));
        UserService userService = userServiceRepository.findById(userServiceId)
                .orElseThrow(() -> new RuntimeException("UserService não encontrado"));
        expedient.setWeekday(weekday);
        expedient.setStartTime(java.time.OffsetDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.parse(startTime), java.time.ZoneOffset.UTC));
        expedient.setEndTime(java.time.OffsetDateTime.of(java.time.LocalDate.now(), java.time.LocalTime.parse(endTime), java.time.ZoneOffset.UTC));
        expedient.setUserService(userService);
        return expedientRepository.save(expedient);
    }
}
