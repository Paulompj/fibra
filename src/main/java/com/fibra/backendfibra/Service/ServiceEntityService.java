package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntityService {

    private final ServiceEntityRepository repository;

    public ServiceEntityService(ServiceEntityRepository repository) {
        this.repository = repository;
    }

    public List<ServiceEntity> findAll() {
        return repository.findAll();
    }

    public Optional<ServiceEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public ServiceEntity save(ServiceEntity service) {
        return repository.save(service);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
