package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dayoffs")
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @PostMapping
    public DayOff create(@RequestBody DayOff dayOff) {
        return dayOffService.save(dayOff);
    }

    @GetMapping
    public List<DayOff> getAll() {
        return dayOffService.findAll();
    }

    @GetMapping("/{id}")
    public DayOff getById(@PathVariable Long id) {
        return dayOffService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dayOffService.delete(id);
    }
}
