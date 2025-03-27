package com.cg.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.cg.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securitydWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges -> exchanges
           //     .pathMatchers("/public/**").permitAll()   // Public routes, no auth required
                .pathMatchers("/identity-service/auth/login", "/identity-service/auth/register").permitAll() // Allow access without authentication
                .pathMatchers(HttpMethod.GET,"/order-service/order").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.GET,"/order-service/testorder").hasRole("ROLE_user")
//                .pathMatchers(HttpMethod.GET,"/ganeshfinancemanagement-loanapplication/loans").hasRole("ROLE_user")
                .pathMatchers(HttpMethod.GET,"/ganeshfinancemanagement-loanapplication/loans").hasRole("ROLE_admin")	
              //  .pathMatchers("/order-service/loadbalance/**").hasRole("ROLE_admin") // ADMIN role only for /admin/**
              //  .pathMatchers("/order-service/feign/**").hasRole("ROLE_user")   // USER role only for /user/**
                .anyExchange().authenticated()              // All other routes require authentication
            )
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION) // Add JWT filter
            .csrf().disable()  // Disable CSRF for stateless APIs
            .build();
    }
}
