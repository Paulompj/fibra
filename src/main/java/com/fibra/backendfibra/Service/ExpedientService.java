package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.Expedient;
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

    public Expedient createExpedient(int weekday, String startTime, String endTime, Long userServiceId) {
        UserService userService = userServiceRepository.findById(userServiceId)
                .orElseThrow(() -> new RuntimeException("UserService não encontrado"));

        Expedient expedient = new Expedient(
                weekday,
                java.time.LocalTime.parse(startTime),
                java.time.LocalTime.parse(endTime),
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
}
