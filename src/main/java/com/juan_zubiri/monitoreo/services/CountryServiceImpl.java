package com.juan_zubiri.monitoreo.services;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juan_zubiri.monitoreo.dao.CountryRepository;
import com.juan_zubiri.monitoreo.dto.CountryDTO;
import com.juan_zubiri.monitoreo.model.Country;
import com.juan_zubiri.monitoreo.response.CountryResponseRest;
import com.juan_zubiri.monitoreo.security.CustomAuthenticationEntryPoint;


@Service
public class CountryServiceImpl implements ICountryService{
	
	@Autowired
	private CountryRepository countryRepository;
	
	//@Autowired
    //private RestTemplate restTemplate; se inyecta solo con la importacion import org.springframework.web.client.RestTemplate;
	
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CountryResponseRest> search() {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	        List<Country> countries = countryRepository.findAll();
	        
	        // Country a CountryDTO
	        List<CountryDTO> countryDTOs = countries.stream()
	            .map(country -> {
	                CountryDTO countryDTO = new CountryDTO();
	                countryDTO.setName(country.getName());
	                countryDTO.setFlagUrl(country.getFlagUrl());
	                return countryDTO;
	            })
	            .collect(Collectors.toList());

	        response.getCountryResponse().setCountry(countryDTOs);  
	        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CountryResponseRest> searchById(Long id) {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	        Optional<Country> country = countryRepository.findById(id);
	        if (country.isPresent()) {
	            //  Country a CountryDTO
	            CountryDTO countryDTO = new CountryDTO();
	            countryDTO.setName(country.get().getName());
	            countryDTO.setFlagUrl(country.get().getFlagUrl());

	            response.getCountryResponse().setCountry(List.of(countryDTO));  
	            response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al consultar", "-1", "País no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	@Transactional
	public ResponseEntity<CountryResponseRest> save(CountryDTO countryDTO) {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	        // debo verificar si existe el país
	        Optional<Country> existingCountry = countryRepository.findByName(countryDTO.getName());
	        if (existingCountry.isPresent()) {
	            response.setMetadata("Error", "409", "El país ya existe en la base de datos");
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	        }

	        // crear Country
	        Country country = new Country();
	        country.setName(countryDTO.getName());
	        country.setFlagUrl(countryDTO.getFlagUrl());

	        // guardo el pais en la BD
	        Country savedCountry = countryRepository.save(country);

	        // convierto Country a CountryDTO
	        CountryDTO savedCountryDTO = new CountryDTO();
	        savedCountryDTO.setName(savedCountry.getName());
	        savedCountryDTO.setFlagUrl(savedCountry.getFlagUrl());

	        response.setMetadata("Éxito", "200", "País guardado correctamente");
	        response.getCountryResponse().setCountry(Collections.singletonList(savedCountryDTO));

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al guardar el país: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}



	@Override
	@Transactional
	public ResponseEntity<CountryResponseRest> update(CountryDTO countryDTO, Long id) {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	        // busco el país por id
	        Optional<Country> countryOptional = countryRepository.findById(id);
	        if (countryOptional.isPresent()) {
	            // llego al país y lo actualizo con los valores que actulice del DTO
	            Country countryToUpdate = countryOptional.get();
	            countryToUpdate.setName(countryDTO.getName());
	            countryToUpdate.setFlagUrl(countryDTO.getFlagUrl());

	            // guardo los cambios
	            countryRepository.save(countryToUpdate);

	            // convertir Country a CountryDTO
	            CountryDTO updatedCountryDTO = new CountryDTO();
	            updatedCountryDTO.setName(countryToUpdate.getName());
	            updatedCountryDTO.setFlagUrl(countryToUpdate.getFlagUrl());

	            response.getCountryResponse().setCountry(List.of(updatedCountryDTO));
	            response.setMetadata("Respuesta exitosa", "00", "País actualizado correctamente");

	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al actualizar", "-1", "País no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al actualizar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}



	@Override
	@Transactional
	public ResponseEntity<CountryResponseRest> deleteById(Long id) {
		 CountryResponseRest response = new CountryResponseRest();
		    try {
		        Optional<Country> countryOptional = countryRepository.findById(id);
		        if (countryOptional.isPresent()) {
		            countryRepository.deleteById(id);
		            response.setMetadata("Respuesta exitosa", "00", "País eliminado correctamente");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al eliminar", "-1", "País no encontrado");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al eliminar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}
	
	  
	@Override
	@Transactional
	public ResponseEntity<CountryResponseRest> loadCountriesFromApi(CountryDTO countryDTO) {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	    	
	        String apiUrl = "https://restcountries.com/v3.1/all?fields=name,flags";
	        
	         // llamada a la API
	        RestTemplate restTemplate = new RestTemplate();
	        
	        // con ResponseEntity debo manejar la respuesta de la API
	        ResponseEntity<String> apiResponse = restTemplate.getForEntity(apiUrl, String.class);

	        if (apiResponse.getStatusCode() == HttpStatus.OK && apiResponse.getBody() != null) {
	        	  // debo deserializr la respuesta JSON coon ObjectMapper
	            ObjectMapper objectMapper = new ObjectMapper();
	            List<Map<String, Object>> countriesApi = objectMapper.readValue(apiResponse.getBody(),
	                new TypeReference<List<Map<String, Object>>>() {});

	            List<Country> newCountries = countriesApi.stream()
	                .map(countryMap -> {
	                    @SuppressWarnings("unchecked")
	                    Map<String, Object> nameMap = (Map<String, Object>) countryMap.get("name");
	                    String name = nameMap != null ? (String) nameMap.get("common") : null;

	                    @SuppressWarnings("unchecked")
	                    Map<String, Object> flagsMap = (Map<String, Object>) countryMap.get("flags");
	                    String flagUrl = flagsMap != null ? (String) flagsMap.get("png") : null;

	                    if (name == null || flagUrl == null) {
	                       logger.warn("Datos inválidos: name={}, flagUrl={}", name, flagUrl);
	                        return null;
	                    }

	                    if (!countryRepository.findByName(name).isPresent()) {
	                        Country newCountry = new Country();
	                        newCountry.setName(name);
	                        newCountry.setFlagUrl(flagUrl);
	                        return newCountry;
	                    }
	                    return null;
	                })
	                .filter(Objects::nonNull)
	                .collect(Collectors.toList());

	            if (!newCountries.isEmpty()) {
	                countryRepository.saveAll(newCountries);
	            }

	            response.setMetadata("Éxito", "200", "País(es) procesados correctamente.");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Advertencia", "404", "No se encontraron datos en la API.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        logger.error("Error al consumir la API: {}", e.getMessage(), e);
	        response.setMetadata("Error", "500", "Error al consumir la API: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}





}
