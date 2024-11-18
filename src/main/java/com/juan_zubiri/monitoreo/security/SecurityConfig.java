package com.juan_zubiri.monitoreo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;

@Configuration
public class SecurityConfig {
	
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/countries", "/api/countries/{id}", 
                                 "/api/plant", "/api/plant/{id}", 
                                 "/api/readings", "/api/readings/{id}", 
                                 "/api/alerts", "/api/alerts/{id}", "/api/alerts/total", "/api/alerts/count/{alertType}",
                                 "/api/sensors", "/api/sensors/{id}", "/api/sensors/count/{sensorType}")
                .authenticated()  // Estas rutas deben ser públicas
                .requestMatchers("/api/users").authenticated()
                .requestMatchers("/api/register/**", "/api/login/**").permitAll()
                .anyRequest().authenticated()
            );

        // Asegúrate de que el filtro JWT solo se aplique a las rutas protegidas
        http.addFilterBefore(jwtRequestFilter, (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}

