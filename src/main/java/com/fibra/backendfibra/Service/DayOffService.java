package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Repository.DayOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayOffService {

    @Autowired
    private DayOffRepository dayOffRepository;

    public DayOff save(DayOff dayOff) {
        return dayOffRepository.save(dayOff);
    }

    public List<DayOff> findAll() {
        return dayOffRepository.findAll();
    }

    public DayOff findById(Long id) {
        return dayOffRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        dayOffRepository.deleteById(id);
    }
}
