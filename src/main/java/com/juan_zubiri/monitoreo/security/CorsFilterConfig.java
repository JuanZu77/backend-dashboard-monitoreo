package com.juan_zubiri.monitoreo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsFilterConfig {

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:4200"); // habilito frontend
        corsConfiguration.addAllowedMethod("*");   // permitir los métodos (GET, POST, PUT, DELETE, etc.)
        corsConfiguration.addAllowedHeader("*");      // permitir los encabezados
        corsConfiguration.setAllowCredentials(true); // habilito credenciales como cookies o tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
