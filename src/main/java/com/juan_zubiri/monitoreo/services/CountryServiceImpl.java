package com.juan_zubiri.monitoreo.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.juan_zubiri.monitoreo.dao.CountryRepository;
import com.juan_zubiri.monitoreo.model.Country;
import com.juan_zubiri.monitoreo.response.CountryResponseRest;

@Service
public class CountryServiceImpl implements ICountryService{
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
    private RestTemplate restTemplate;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CountryResponseRest> search() {
		 CountryResponseRest response = new CountryResponseRest();
		    try {
		    	List<Country> countries = countryRepository.findAll(); 
		        response.getCountryResponse().setCountry(countries);
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
		            response.getCountryResponse().setCountry(List.of(country.get()));
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
	public ResponseEntity<CountryResponseRest> save(Country country) {
		  CountryResponseRest response = new CountryResponseRest();
		    try {
		        // verifico si pais existe
		        Optional<Country> existingCountry = countryRepository.findByName(country.getName());
		        if (existingCountry.isPresent()) {
		            response.setMetadata("Error", "409", "El país ya existe en la base de datos");
		            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		        }

		        // guardo el pais
		        Country savedCountry = countryRepository.save(country);

		        // respuesta
		        response.setMetadata("Éxito", "200", "País guardado correctamente");
		        response.getCountryResponse().setCountry(Collections.singletonList(savedCountry));
		        return ResponseEntity.ok(response);

		    } catch (Exception e) {
		        response.setMetadata("Error", "500", "Error al guardar el país: " + e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	@Transactional
	public ResponseEntity<CountryResponseRest> update(Country country, Long id) {
		 CountryResponseRest response = new CountryResponseRest();
		    try {
		        Optional<Country> countryOptional = countryRepository.findById(id);
		        if (countryOptional.isPresent()) {
		            Country countryToUpdate = countryOptional.get();
		            countryToUpdate.setName(country.getName()); 
		           
		            countryRepository.save(countryToUpdate);
		            response.getCountryResponse().setCountry(List.of(countryToUpdate));
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
	public ResponseEntity<CountryResponseRest> loadCountriesFromApi() {
	    CountryResponseRest response = new CountryResponseRest();
	    try {
	     
	        String apiUrl = "https://restcountries.com/v3/all";

	        //comsumo api
	        ResponseEntity<Object[]> apiResponse = restTemplate.getForEntity(apiUrl, Object[].class);
	        Object[] countriesFromApi = apiResponse.getBody();

	        // debo verificar la existencia de paises
	        if (countriesFromApi != null) {
	            List<Country> countries = Arrays.stream(countriesFromApi)
	                    .map(countryObject -> {
	                        Map<?, ?> countryMap = (Map<?, ?>) countryObject;

	                        String name = ((Map<?, ?>) countryMap.get("name")).get("common").toString();
	                        String flagUrl = ((Map<?, ?>) countryMap.get("flags")).get("png").toString();

	                        // verifico si existe el pais
	                        Optional<Country> existingCountry = countryRepository.findByName(name);
	                        if (existingCountry.isPresent()) {
	                            return null; // bloqueo el duplicado
	                        }

	                        Country country = new Country();
	                        country.setName(name);
	                        country.setFlagUrl(flagUrl);
	                        return country;
	                    })
	                    .filter(Objects::nonNull) 
	                    .collect(Collectors.toList());

	            // guardo paises de la api en la bd
	            countryRepository.saveAll(countries);

	            response.setMetadata("Carga exitosa", "00", "Países cargados desde la API");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error en la carga", "-1", "No se encontraron datos en la API");
	            return ResponseEntity.noContent().build();
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error en la carga", "-1", "Error al consumir la API");
	        return ResponseEntity.status(500).body(response);
	    }
	}
}
