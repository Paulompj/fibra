package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Service.ExpedientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        Long userId = request.getUserId();
        Long serviceId = request.getServiceId();
        UserService userService = expedientService.getOrCreateUserService(userId, serviceId);
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
    public ResponseEntity<Void> deleteExpedient(@PathVariable Long id) {
        expedientService.deleteExpedient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expedient> updateExpedient(@PathVariable Long id, @RequestBody ExpedientRequest request) {
        Long userId = request.getUserId();
        Long serviceId = request.getServiceId();
        UserService userService = expedientService.getOrCreateUserService(userId, serviceId);
        Expedient updated = expedientService.updateExpedient(
            id,
            request.getWeekday(),
            request.getStartTime(),
            request.getEndTime(),
            userService.getId()
        );
        return ResponseEntity.ok(updated);
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
