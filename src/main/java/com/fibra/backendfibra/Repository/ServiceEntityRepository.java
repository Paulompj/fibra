package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Integer> {
    List<ServiceEntity> findByNameContainingIgnoreCase(String name);

}
