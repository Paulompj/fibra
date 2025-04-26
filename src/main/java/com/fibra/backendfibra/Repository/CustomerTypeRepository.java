package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
