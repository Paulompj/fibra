package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserServiceRepository extends JpaRepository<UserService, Long> {
    List<UserService> findByServiceId(Long serviceId);

}
