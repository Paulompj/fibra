package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
    public Customer update(Long id, Customer customer) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setFullName(customer.getFullName());
                    existingCustomer.setCustomerType(customer.getCustomerType());
                    existingCustomer.setAge(customer.getAge());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setPhotoUrl(customer.getPhotoUrl());
                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> new RuntimeException("CustomerType not found with id " + id));
    }
    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name);
    }



}
