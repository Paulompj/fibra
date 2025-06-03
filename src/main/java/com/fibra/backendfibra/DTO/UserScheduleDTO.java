// src/main/java/com/fibra/backendfibra/DTO/UserScheduleDTO.java
package com.fibra.backendfibra.DTO;

import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.TimeOff;

public class UserScheduleDTO {
    private DayOff dayOff;
    private Expedient expediente;
    private TimeOff timeOff;

    public UserScheduleDTO() {}

    public UserScheduleDTO(DayOff dayOff, Expedient expediente, TimeOff timeOff) {
        this.dayOff = dayOff;
        this.expediente = expediente;
        this.timeOff = timeOff;
    }

    public DayOff getDayOff() { return dayOff; }
    public void setDayOff(DayOff dayOff) { this.dayOff = dayOff; }

    public Expedient getExpedient() { return expediente; }
    public void setExpedient(Expedient expediente) { this.expediente = expediente; }

    public TimeOff getTimeOff() { return timeOff; }
    public void setTimeOff(TimeOff timeOff) { this.timeOff = timeOff; }
}