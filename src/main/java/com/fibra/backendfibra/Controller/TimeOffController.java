package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.TimeOff;
import com.fibra.backendfibra.Service.TimeOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-offs")
public class TimeOffController {

    @Autowired
    private TimeOffService timeOffService;

    @PostMapping
    public TimeOff createTimeOff(@RequestBody TimeOff timeOff) {
        return timeOffService.createTimeOff(timeOff);
    }

    @GetMapping
    public List<TimeOff> getAllTimeOffs() {
        return timeOffService.getAllTimeOffs();
    }

    @GetMapping("/user-service/{userServiceId}")
    public List<TimeOff> getByUserServiceId(@PathVariable Long userServiceId) {
        return timeOffService.getByUserServiceId(userServiceId);
    }
}
