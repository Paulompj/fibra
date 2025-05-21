package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.TimeOff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeOffRepository extends JpaRepository<TimeOff, Long> {
    List<TimeOff> findByUserServiceId(Long userServiceId);
}
