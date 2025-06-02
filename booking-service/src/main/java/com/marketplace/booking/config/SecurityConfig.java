package com.marketplace.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.marketplace.booking.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtFilter;
    public SecurityConfig(JwtAuthFilter f) { this.jwtFilter = f; }

    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception {
      http.csrf(csrf->csrf.disable())
          .authorizeHttpRequests(auth->auth
              .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
           // 1) confirm y cancel: subrutas específicas
              .requestMatchers(HttpMethod.POST, "/bookings/*/confirm")
                .hasAnyRole("PROVEEDOR","CREADOR","ADMIN")
              .requestMatchers(HttpMethod.POST, "/bookings/*/cancel")
                .hasAnyRole("PROVEEDOR","CREADOR","ADMIN")

              // 2) creación de reserva: solo POST EXACTO a /bookings
              .requestMatchers(HttpMethod.POST, "/bookings")
                .hasRole("CLIENTE")

              // 3) listado de reservas: cualquier GET bajo /bookings/**
              .requestMatchers(HttpMethod.GET, "/bookings/**")
                .hasAnyRole("PROVEEDOR","CLIENTE","CREADOR","ADMIN")
                .anyRequest().authenticated()
          )
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }
}

