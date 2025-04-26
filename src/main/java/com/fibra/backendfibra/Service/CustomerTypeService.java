package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Repository.CustomerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTypeService {

    @Autowired
    private CustomerTypeRepository repository;

    public List<CustomerType> findAll() {
        return repository.findAll();
    }

    public Optional<CustomerType> findById(Long id) {
        return repository.findById(id);
    }

    public CustomerType save(CustomerType customerType) {
        return repository.save(customerType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CustomerType update(Long id, CustomerType customerType) {
        return repository.findById(id)
                .map(existingCustomerType -> {
                    existingCustomerType.setName(customerType.getName());
                    return repository.save(existingCustomerType);
                })
                .orElseThrow(() -> new RuntimeException("CustomerType not found with id " + id));
    }

}
