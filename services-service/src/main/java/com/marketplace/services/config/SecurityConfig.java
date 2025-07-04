package com.marketplace.services.config;

import com.marketplace.services.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(     
                  "/swagger-ui/**",
                  "/v3/api-docs/**"
              ).permitAll()
              .requestMatchers(HttpMethod.GET, "/servicios/**").hasAnyRole("CLIENTE","CREADOR","ADMIN","PROVEEDOR")
              .requestMatchers(HttpMethod.POST, "/servicios/**").hasAnyRole("CREADOR","PROVEEDOR")
              .anyRequest().authenticated()
          )
          .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
