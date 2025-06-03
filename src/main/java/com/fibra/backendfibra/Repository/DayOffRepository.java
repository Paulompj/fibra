package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DayOffRepository extends JpaRepository<DayOff, Long> {
    List<DayOff> findByUserServiceId(Long userServiceId);
}
