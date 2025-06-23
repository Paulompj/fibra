package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.*;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Model.Customer;
import com.fibra.backendfibra.Model.CustomerType;
import com.fibra.backendfibra.Repository.UserRepository;
import com.fibra.backendfibra.Repository.CustomerRepository;
import com.fibra.backendfibra.Repository.AppointmentRepository;
import com.fibra.backendfibra.Service.UserService;
import com.fibra.backendfibra.Service.UserServiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fibra.backendfibra.Model.DayOff;
import com.fibra.backendfibra.Model.Expedient;
import com.fibra.backendfibra.Model.TimeOff;
import com.fibra.backendfibra.Repository.UserServiceRepository;
import com.fibra.backendfibra.Repository.ExpedientRepository;
import com.fibra.backendfibra.Repository.DayOffRepository;
import com.fibra.backendfibra.Repository.TimeOffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Tag(name = "Professionais", description = "Operações relacionadas a Profissionais ou Users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserServiceService userServiceService;
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final ExpedientRepository expedientRepository;
    private final DayOffRepository dayOffRepository;
    private final TimeOffRepository timeOffRepository;
    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;

    public UserController(UserService userService, UserRepository userRepository,
                         UserServiceRepository userServiceRepository,
                         ExpedientRepository expedientRepository,
                         DayOffRepository dayOffRepository,
                         TimeOffRepository timeOffRepository,
                         CustomerRepository customerRepository,
                         AppointmentRepository appointmentRepository,
                          UserServiceService userServiceService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userServiceRepository = userServiceRepository;
        this.expedientRepository = expedientRepository;
        this.dayOffRepository = dayOffRepository;
        this.timeOffRepository = timeOffRepository;
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
        this.userServiceService = userServiceService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/schedule")
    public ResponseEntity<UserScheduleDTO> getUserScheduleByUserAndService(@RequestParam Long userId, @RequestParam Long serviceId) {
        var userService = userServiceRepository.findByUserIdAndServiceId(userId, serviceId);
        if (userService == null) {
            return ResponseEntity.notFound().build();
        }
        Long userServiceId = userService.getId();
        var expedients = expedientRepository.findByUserServiceId(userServiceId);
        var dayOffs = dayOffRepository.findByUserServiceId(userServiceId);
        var timeOffs = timeOffRepository.findByUserServiceId(userServiceId);
        // Para compatibilidade com o DTO atual, retorna o primeiro de cada lista (ou null)
        var dto = new UserScheduleDTO(
            dayOffs.isEmpty() ? null : dayOffs.get(0),
            expedients.isEmpty() ? null : expedients.get(0),
            timeOffs.isEmpty() ? null : timeOffs.get(0)
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/customers-with-appointments")
    public ResponseEntity<?> getCustomersWithAppointments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().body("O número da página deve ser maior ou igual a 1.");
        }
        Page<Customer> customerPage = customerRepository.findAll(PageRequest.of(page - 1, size));
        List<CustomerWithAppointmentsDTO> data = customerPage.getContent().stream().map(customer -> {
            int count = appointmentRepository.countByCustomerId(customer.getId());
            CustomerType type = customer.getCustomerType();
            CustomerWithAppointmentsDTO.CustomerTypeDTO typeDTO = new CustomerWithAppointmentsDTO.CustomerTypeDTO(
                    type != null ? String.valueOf(type.getId()) : null,
                    type != null ? type.getName() : null
            );
            return new CustomerWithAppointmentsDTO(
                    String.valueOf(customer.getId()),
                    customer.getFullName(),
                    customer.getPhone(),
                    customer.getAge(),
                    customer.getAddress(),
                    customer.getPhotoUrl(),
                    typeDTO,
                    count
            );
        }).toList();
        return ResponseEntity.ok(new CustomersWithAppointmentsResponseDTO(
                String.valueOf(customerPage.getNumber() + 1),
                data,
                customerPage.getSize(),
                customerPage.getTotalPages(),
                customerPage.getTotalElements()
        ));
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/with-services")
    public ResponseEntity<?> getUsersWithServices(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().body("O número da página deve ser maior ou igual a 1.");
        }
        Page<UserWithServicesDTO> userPage = userService.findUsersWithServices(PageRequest.of(page - 1, size));
        return ResponseEntity.ok(new Object() {
            public final int number = userPage.getNumber() + 1;
            public final Object data = userPage.getContent();
            public final int size = userPage.getSize();
            public final int totalPages = userPage.getTotalPages();
            public final long totalElements = userPage.getTotalElements();
        });
    }

    @GetMapping("/professionals-services-expedients")
    public ResponseEntity<?> getProfessionalsWithServicesAndExpedients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (page < 1) {
            return ResponseEntity.badRequest().body("O número da página deve ser maior ou igual a 1.");
        }
        Page<ProfessionalServiceExpedientResponseDTO> resultPage = userServiceService.getProfessionalsWithServicesAndExpedients(PageRequest.of(page - 1, size));
        Map<String, Object> response = new HashMap<>();
        response.put("number", resultPage.getNumber() + 1);
        response.put("data", resultPage.getContent());
        response.put("size", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("totalElements", resultPage.getTotalElements());
        return ResponseEntity.ok(response);
    }

    // PUT /users/{id} - Usuário logado pode alterar apenas seus próprios dados (exceto senha, mesmo se for ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
        if (!user.getEmail().equals(username)) {
            return ResponseEntity.status(403).body("Você só pode editar seu próprio usuário.");
        }
        // Nunca permite alteração de senha nesta rota, mesmo se for ADMIN
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        if (request.getRole() != null) user.setRole(User.Role.fromString(request.getRole()));
        userService.save(user);
        return ResponseEntity.ok("Usuário atualizado com sucesso. (Senha não pode ser alterada por esta rota)");
    }

    // PUT /users/admin/{id} - Somente ADMIN pode alterar qualquer usuário (inclusive senha)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<?> adminUpdateUser(@PathVariable Integer id, @RequestBody UserAdminUpdateRequest request, Authentication authentication) {
        // Verificação extra para garantir que só ADMIN pode acessar
        if (authentication == null || authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(401).body("unauthorized");
        }
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        if (request.getRole() != null) user.setRole(User.Role.fromString(request.getRole()));
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword());
        }
        System.out.println("Atualizando usuário: " + user.getEmail() + " com nova senha: " + request.getPassword() + " e role: " + request.getRole());
        userService.save(user);
        return ResponseEntity.ok("Usuário atualizado com sucesso pelo ADMIN (senha pode ter sido alterada).");
    }
}
