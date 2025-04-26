package com.fibra.backendfibra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Forma nova de desativar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Forma nova de liberar tudo
                )
                .httpBasic(withDefaults()) // Deixa habilitado HTTP Basic (opcional)
                .build();
    }
}
