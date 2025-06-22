package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.TimeOff;

import java.util.List;

public interface TimeOffService {
    TimeOff createTimeOff(TimeOff timeOff);
    List<TimeOff> getAllTimeOffs();
    List<TimeOff> getByUserServiceId(Long userServiceId);
    TimeOff updateTimeOff(Long id, TimeOff updatedTimeOff);
}
