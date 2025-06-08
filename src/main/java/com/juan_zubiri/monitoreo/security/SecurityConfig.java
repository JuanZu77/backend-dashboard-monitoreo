package com.juan_zubiri.monitoreo.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @SuppressWarnings("deprecation")
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(authz -> authz
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
                .requestMatchers(HttpMethod.GET, "/api/countries", "/api/countries/{id}",
                                 "/api/plant", "/api/plant/{id}",
                                 "/api/readings", "/api/readings/{id}",
                                 "/api/alerts", "/api/alerts/{id}", "/api/alerts/total", "/api/alerts/count/{alertType}",
                                 "/api/sensors", "/api/sensors/{id}", "/api/sensors/count/{sensorType}")
                .authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}/update-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/users/find-by-email").permitAll()
                .requestMatchers("/api/users").authenticated()
                .requestMatchers("/api/register/**", "/api/login/**").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(customAuthenticationEntryPoint)) // manejo  excepciones de autenticación
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    @Bean
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200", 
                "https://monitoreoapp-443123.rj.r.appspot.com"
            ));
        configuration.addAllowedMethod("*"); 
        configuration.addAllowedHeader("*"); 
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

