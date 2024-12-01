package com.juan_zubiri.monitoreo.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

//@Bean
//RestTemplate restTemplate() {
//  RestTemplate restTemplate = new RestTemplate();
//  // Agregar el conversor de Jackson
//  restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//  return restTemplate;
//}