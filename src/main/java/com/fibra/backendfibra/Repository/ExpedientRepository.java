package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.Expedient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpedientRepository extends JpaRepository<Expedient, Long> {
    List<Expedient> findByUserServiceId(Long userServiceId);
}
