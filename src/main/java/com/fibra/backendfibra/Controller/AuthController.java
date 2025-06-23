package com.fibra.backendfibra.Controller;

import com.fibra.backendfibra.DTO.LoginRequest;
import com.fibra.backendfibra.DTO.LoginResponse;
import com.fibra.backendfibra.DTO.UserProfileDTO;
import com.fibra.backendfibra.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.fibra.backendfibra.Model.User;
import com.fibra.backendfibra.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Falha na autenticação");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
        // Retorne apenas os dados necessários
        return ResponseEntity.ok(new UserProfileDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRole()));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken() {
        // O token já será validado pelo filtro de autenticação JWT
        // Se chegou aqui, o token é válido
        return ResponseEntity.ok(true);
    }
}
