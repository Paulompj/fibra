package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Service.DayOffService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Dias Livre (Day-Off) ", description = "Operações relacionadas a tempo livre")
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
