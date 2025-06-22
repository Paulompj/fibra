package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.TimeOff;
import com.fibra.backendfibra.Repository.TimeOffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeOffServiceImpl implements TimeOffService {

    @Autowired
    private TimeOffRepository timeOffRepository;

    @Override
    public TimeOff createTimeOff(TimeOff timeOff) {
        return timeOffRepository.save(timeOff);
    }

    @Override
    public List<TimeOff> getAllTimeOffs() {
        return timeOffRepository.findAll();
    }

    @Override
    public List<TimeOff> getByUserServiceId(Long userServiceId) {
        return timeOffRepository.findByUserServiceId(userServiceId);
    }

    public TimeOff updateTimeOff(Long id, TimeOff updatedTimeOff) {
        return timeOffRepository.findById(id)
                .map(existing -> {
                    existing.setStartDateTime(updatedTimeOff.getStartDateTime());
                    existing.setEndDateTime(updatedTimeOff.getEndDateTime());
                    existing.setUserService(updatedTimeOff.getUserService());
                    return timeOffRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("TimeOff not found with id " + id));
    }
}
