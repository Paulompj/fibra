package com.fibra.backendfibra.Repository;

import com.fibra.backendfibra.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
    long countByRole(User.Role role);
}
