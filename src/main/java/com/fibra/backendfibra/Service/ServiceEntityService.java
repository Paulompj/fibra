package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.DTO.ServiceWithUsersRequest;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntityService {

    private final ServiceEntityRepository repository;
    private final UserServiceService userServiceService;

    public ServiceEntityService(ServiceEntityRepository repository, UserServiceService userServiceService) {
        this.repository = repository;
        this.userServiceService = userServiceService;
    }

    public Page<ServiceEntity> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public List<ServiceEntity> findAllServices() {
        return repository.findAll();
    }
    public Optional<ServiceEntity> findById(Integer id) {
        return repository.findById(id);
    }

    public ServiceEntity save(ServiceEntity serviceEntity) {
        return repository.save(serviceEntity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public ServiceEntity createServiceWithUsers(ServiceWithUsersRequest request) {
        ServiceEntity service = new ServiceEntity();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setDuration(request.getDuration());

        ServiceEntity savedService = repository.save(service);


        for (Long userId : request.getUserIds()) {
            userServiceService.createUserService(userId, Long.valueOf(savedService.getId()));
        }

        return savedService;
    }
}
