package com.graduation.backend.config;

import com.graduation.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Swagger/OpenAPI endpoints
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        // Public endpoints
                        .requestMatchers("/api/ping").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // User registration - public (for initial user creation)
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        // User management - only admins (GET, PUT, DELETE)
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN")
                        .requestMatchers("/api/users/**").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN")
                        
                        // Branch creation - public (for initial setup)
                        .requestMatchers(HttpMethod.POST, "/api/branches").permitAll()
                        // Branch management - admins and branch managers
                        .requestMatchers("/api/branches/**").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN", "BRANCH_MANAGER")
                        
                        // Product management - admins
                        .requestMatchers("/api/products/**").hasAnyRole("SYSTEM_ADMIN", "COMPANY_ADMIN")
                        
                        // Stock and Sales - all authenticated users (branch-specific checks in service layer)
                        .requestMatchers("/api/stocks/**").authenticated()
                        .requestMatchers("/api/sales/**").authenticated()
                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
