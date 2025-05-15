package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.UserService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserServiceRepository extends JpaRepository<UserService, Long> {
}
