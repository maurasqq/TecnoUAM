
package com.tecnouam.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Login público
                        .requestMatchers("/api/auth/login").permitAll()

                        // USER y ADMIN pueden consultar equipos y préstamos
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/equipos",
                                "/api/equipos/**",
                                "/api/prestamos",
                                "/api/prestamos/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Solo ADMIN puede crear, modificar y eliminar
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/equipos",
                                "/api/prestamos"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/equipos/**",
                                "/api/prestamos/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/equipos/**"
                        ).hasRole("ADMIN")

                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
