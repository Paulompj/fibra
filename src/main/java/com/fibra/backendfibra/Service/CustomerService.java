package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Repository.CustomerRepository;
import com.fibra.backendfibra.Repository.CustomerTypeRepository;
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
    @Autowired
    private CustomerTypeRepository customerTypeRepository;

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
                    existingCustomer.setAge(customer.getAge());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setPhotoUrl(customer.getPhotoUrl());

                    // Busca segura do CustomerType pelo ID
                    Long typeId = Long.valueOf(customer.getCustomerType().getId());
                    CustomerType existingType = customerTypeRepository.findById(typeId)
                            .orElseThrow(() -> new RuntimeException("Tipo de cliente não encontrado com id " + typeId));
                    existingCustomer.setCustomerType(existingType);

                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id " + id));
    }
    public List<Customer> findCustomersByName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name);
    }



}
