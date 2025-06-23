package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.DTO.ServiceWithUsersRequest;
import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import com.fibra.backendfibra.Repository.DayOffRepository;
import com.fibra.backendfibra.Repository.TimeOffRepository;
import com.fibra.backendfibra.Repository.ExpedientRepository;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceEntityService {

    private final ServiceEntityRepository repository;
    private final UserServiceService userServiceService;
    private final DayOffRepository dayOffRepository;
    private final TimeOffRepository timeOffRepository;
    private final ExpedientRepository expedientRepository;
    private final UserServiceRepository userServiceRepository;

    public ServiceEntityService(ServiceEntityRepository repository, UserServiceService userServiceService, DayOffRepository dayOffRepository, TimeOffRepository timeOffRepository, ExpedientRepository expedientRepository, UserServiceRepository userServiceRepository) {
        this.repository = repository;
        this.userServiceService = userServiceService;
        this.dayOffRepository = dayOffRepository;
        this.timeOffRepository = timeOffRepository;
        this.expedientRepository = expedientRepository;
        this.userServiceRepository = userServiceRepository;
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
        Long userServiceId = Long.valueOf(id);
        dayOffRepository.deleteAll(dayOffRepository.findByUserServiceId(userServiceId));
        timeOffRepository.deleteAll(timeOffRepository.findByUserServiceId(userServiceId));
        expedientRepository.deleteAll(expedientRepository.findByUserServiceId(userServiceId));
        // Remover todos os vínculos de users_services antes de deletar o serviço
        userServiceRepository.deleteAll(userServiceRepository.findByServiceId(userServiceId));
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
    public List<ServiceEntity> findServicesByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
    public ServiceEntity updateServiceEntity(Long id, ServiceEntity updatedServiceEntity) {
        // Busca a ServiceEntity pelo ID para garantir que existe
        ServiceEntity existingServiceEntity = repository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("ServiceEntity não encontrada com o ID: " + id));

        // Atualiza os campos necessários
        if (updatedServiceEntity.getName() != null) {
            existingServiceEntity.setName(updatedServiceEntity.getName());
        }

        if (updatedServiceEntity.getDescription() != null) {
            existingServiceEntity.setDescription(updatedServiceEntity.getDescription());
        }

        if (updatedServiceEntity.getDuration() != null) {
            existingServiceEntity.setDuration(updatedServiceEntity.getDuration());
        }

        // Salva a entidade atualizada
        return repository.save(existingServiceEntity);
    }

    public ServiceEntity update(Integer id, ServiceEntity updated) {
        ServiceEntity entity = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("ServiceEntity não encontrado"));
        entity.setName(updated.getName());
        entity.setDescription(updated.getDescription());
        entity.setDuration(updated.getDuration());
        return repository.save(entity);
    }
    public ServiceEntity updateServiceProfessionals(Integer serviceId, List<Long> userIds) {
        ServiceEntity serviceEntity = repository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + serviceId));
        // Remove todos os vínculos antigos
        userServiceRepository.deleteAll(userServiceRepository.findByServiceId(Long.valueOf(serviceId)));
        // Cria novos vínculos
        for (Long userId : userIds) {
            userServiceService.createUserService(userId, Long.valueOf(serviceId));
        }
        return serviceEntity;
    }

}
