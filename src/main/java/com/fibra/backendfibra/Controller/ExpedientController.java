package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Service.ExpedientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expedients")
public class ExpedientController {

    @Autowired
    private ExpedientService expedientService;

    @PostMapping
    public Expedient createExpedient(@RequestBody ExpedientRequest request) {
        return expedientService.createExpedient(
                request.getWeekday(),
                request.getStartTime(),
                request.getEndTime(),
                request.getUserServiceId()
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

    public static class ExpedientRequest {
        private int weekday;
        private String startTime;
        private String endTime;
        private Long userServiceId;

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

        public Long getUserServiceId() {
            return userServiceId;
        }

        public void setUserServiceId(Long userServiceId) {
            this.userServiceId = userServiceId;
        }
    }
}
