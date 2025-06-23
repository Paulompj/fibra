package com.fibra.backendfibra.Repository;


import com.fibra.backendfibra.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFullNameContainingIgnoreCase(String fullName);

    @org.springframework.data.jpa.repository.Query("SELECT c.customerType.name, COUNT(c) FROM Customer c GROUP BY c.customerType.name")
    java.util.List<Object[]> countByTypeGroup();
}
