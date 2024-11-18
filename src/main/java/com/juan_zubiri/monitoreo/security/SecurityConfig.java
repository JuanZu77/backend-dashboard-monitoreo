package com.juan_zubiri.monitoreo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())  
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/countries", "/api/countries/{id}", 
	            		"/api/plant", "/api/plant/{id}", 
	            		"/api/readings", "/api/readings/{id}", 
	            		"/api/alerts", "/api/alerts/{id}", "/api/alerts/total", "/api/alerts/count/{alertType}",
	            		"/api/sensors", "/api/sensors/{id}", "/api/sensors/count/{sensorType}").permitAll()  
	            .requestMatchers("/api/register").permitAll()  
	            .requestMatchers("/api/user/**").authenticated()  
	            .anyRequest().authenticated() 
	        );
	    return http.build();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
}

