package com.fibra.backendfibra.Service;

import com.fibra.backendfibra.Model.ServiceEntity;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.UserService;
import com.fibra.backendfibra.Repository.ServiceEntityRepository;
import com.fibra.backendfibra.Repository.UserRepository;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.fibra.backendfibra.DTO.UserScheduleResponseDTO;
import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.TimeOff;
import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Model.Appointment;
import com.fibra.backendfibra.Repository.ExpedientRepository;
import com.fibra.backendfibra.Repository.TimeOffRepository;
import com.fibra.backendfibra.Repository.DayOffRepository;
import com.fibra.backendfibra.Repository.AppointmentRepository;
import org.springframework.data.domain.Page;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Service
public class UserServiceService {
    @Autowired
    private UserServiceRepository userServiceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceEntityRepository serviceRepository;
    @Autowired
    private ExpedientRepository expedientRepository;
    @Autowired
    private TimeOffRepository timeOffRepository;
    @Autowired
    private DayOffRepository dayOffRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public UserScheduleResponseDTO getUserScheduleByUserAndService(Long userId, Long serviceId) {
        UserService userService = userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
        if (userService == null) throw new RuntimeException("UserService não encontrado");
        User user = userService.getUser();
        List<Expedient> expedients = expedientRepository.findByUserServiceId(userService.getId());
        List<TimeOff> timeOffs = timeOffRepository.findByUserServiceId(userService.getId());
        List<DayOff> dayOffs = dayOffRepository.findByUserServiceId(userService.getId());
        List<Appointment> appointments = appointmentRepository.findAll().stream()
            .filter(a -> a.getProfessional().getId().longValue() == userId && a.getService().getId().longValue() == serviceId)
            .toList();

        List<UserScheduleResponseDTO.ExpedientDTO> expedientDTOs = expedients.stream().map(e -> {
            UserScheduleResponseDTO.ExpedientDTO dto = new UserScheduleResponseDTO.ExpedientDTO();
            dto.id = e.getId();
            dto.weekday = e.getWeekday();
            dto.startTime = e.getStartTime().toString().substring(0,5);
            dto.endTime = e.getEndTime().toString().substring(0,5);
            return dto;
        }).toList();
        List<UserScheduleResponseDTO.TimeOffDTO> timeOffDTOs = timeOffs.stream().map(t -> {
            UserScheduleResponseDTO.TimeOffDTO dto = new UserScheduleResponseDTO.TimeOffDTO();
            dto.id = t.getId();
            dto.startDateTime = t.getStartDateTime().toString();
            dto.endDateTime = t.getEndDateTime().toString();
            return dto;
        }).toList();
        List<UserScheduleResponseDTO.DayOffDTO> dayOffDTOs = dayOffs.stream().map(d -> {
            UserScheduleResponseDTO.DayOffDTO dto = new UserScheduleResponseDTO.DayOffDTO();
            dto.id = d.getId();
            dto.dayOff = d.getDayOff().atStartOfDay().toString();
            return dto;
        }).toList();
        List<UserScheduleResponseDTO.AppointmentDTO> appointmentDTOs = appointments.stream().map(a -> {
            UserScheduleResponseDTO.AppointmentDTO dto = new UserScheduleResponseDTO.AppointmentDTO();
            dto.id = a.getId().toString();
            dto.dateTime = a.getDateTime().toString();
            return dto;
        }).toList();
        return new UserScheduleResponseDTO(
            user.getId().longValue(),
            user.getFullName(),
            expedientDTOs,
            timeOffDTOs,
            dayOffDTOs,
            appointmentDTOs
        );
    }
    public List<UserService> createUserServices(List<Long> userIds, Long serviceId) {
        ServiceEntity service = serviceRepository.findById(Math.toIntExact(serviceId)).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        return userIds.stream().map(userId -> {
            User user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userId));
            UserService userService = new UserService(user, service);
            return userServiceRepository.save(userService);
        }).collect(Collectors.toList());
    }

    public UserService findByUserIdAndServiceId(Long userId, Long serviceId) {
        return userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
    }
    public List<UserService> findAll() {
        return userServiceRepository.findAll();
    }

    public Page<UserService> findAll(Pageable pageable) {
        return userServiceRepository.findAll(pageable);
    }

    public List<User> getUsersByServiceId(Long serviceId) {
        return userServiceRepository.findByServiceId(serviceId)
                .stream()
                .map(UserService::getUser)
                .collect(Collectors.toList());
    }
    public UserService createUserService(Long userId, Long serviceId) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        ServiceEntity service = serviceRepository.findById(Math.toIntExact(serviceId)).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        UserService userService = new UserService(user, service);
        return userServiceRepository.save(userService);
    }
}
