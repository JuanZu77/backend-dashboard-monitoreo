package com.juan_zubiri.monitoreo.security;

import java.util.Arrays;

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
        corsConfiguration.setAllowedOrigins(Arrays.asList(
        	    "http://localhost:4200", 
        	    "https://monitoreoapp-443123.rj.r.appspot.com"
        	));
        corsConfiguration.addAllowedMethod("*");   // permitir los métodos (GET, POST, PUT, DELETE, etc.)
        corsConfiguration.addAllowedHeader("*");      // permitir los encabezados
        corsConfiguration.setAllowCredentials(true); // habilito credenciales como cookies o tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
