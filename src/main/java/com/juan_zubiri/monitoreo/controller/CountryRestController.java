package com.juan_zubiri.monitoreo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan_zubiri.monitoreo.dto.CountryDTO;
import com.juan_zubiri.monitoreo.response.CountryResponseRest;
import com.juan_zubiri.monitoreo.services.ICountryService;

@RestController
@RequestMapping("/api")
public class CountryRestController {
	
	@Autowired
	private ICountryService countryService;

	 @GetMapping("/countries")
	public ResponseEntity<CountryResponseRest> search(){
		
		 ResponseEntity<CountryResponseRest> response = countryService.search();
		 return response;
	}
	 
	 @GetMapping("/countries/{id}")
	public ResponseEntity<CountryResponseRest> searchCountriesById(@PathVariable Long id){
		
		 ResponseEntity<CountryResponseRest> response = countryService.searchById(id);
		 return response;
	}
	 
	 
	 @PostMapping("/countries")
		public ResponseEntity<CountryResponseRest> saveCountries(@RequestBody CountryDTO country){
			
			 ResponseEntity<CountryResponseRest> response = countryService.save(country);
			 return response;
		} 
	 
	 
	 @PutMapping("/countries/{id}")
	public ResponseEntity<CountryResponseRest> update(@RequestBody CountryDTO country, @PathVariable Long id){
		
		 ResponseEntity<CountryResponseRest> response = countryService.update(country, id);
		 return response;
	}
	 
	 
	 @DeleteMapping("/countries/{id}")
	public ResponseEntity<CountryResponseRest> delete(@PathVariable Long id){
		
		 ResponseEntity<CountryResponseRest> response = countryService.deleteById(id);
		 return response;
	}
	 
}
