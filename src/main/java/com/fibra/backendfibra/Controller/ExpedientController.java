package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Service.ExpedientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Expedientes", description = "Operações relacionadas a cadastro de Expedientes")
@RestController
@RequestMapping("/expedients")
public class ExpedientController {

    @Autowired
    private ExpedientService expedientService;

    @PostMapping
    public Expedient createExpedient(@RequestBody ExpedientRequest request) {
        // Buscar o UserServiceId a partir de userId e serviceId
        Long userId = request.getUserId();
        Long serviceId = request.getServiceId();
        UserService userService = expedientService.findUserServiceByUserIdAndServiceId(userId, serviceId);
        if (userService == null) {
            throw new RuntimeException("UserService não encontrado para userId=" + userId + " e serviceId=" + serviceId);
        }
        return expedientService.createExpedient(
                request.getWeekday(),
                request.getStartTime(),
                request.getEndTime(),
                userService.getId()
        );
    }

    @GetMapping
    public List<Expedient> getAllExpedients() {
        return expedientService.findAll();
    }

    @GetMapping("/user-service/{userServiceId}")
    public List<Expedient> getByUserServiceId(@PathVariable Long userServiceId) {
        return expedientService.getByUserServiceId(userServiceId);
    }

    @DeleteMapping("/{id}")
    public void deleteExpedient(@PathVariable Long id) {
        expedientService.deleteExpedient(id);
    }

    public static class ExpedientRequest {
        private int weekday;
        private String startTime;
        private String endTime;
        private Long userId;
        private Long serviceId;

        public int getWeekday() {
            return weekday;
        }

        public void setWeekday(int weekday) {
            this.weekday = weekday;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

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
}
