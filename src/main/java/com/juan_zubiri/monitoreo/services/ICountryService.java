package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.CountryDTO;
import com.juan_zubiri.monitoreo.response.CountryResponseRest;

public interface ICountryService {

	public ResponseEntity<CountryResponseRest> search();
	public ResponseEntity<CountryResponseRest> searchById(Long id);
	
	public ResponseEntity<CountryResponseRest> save(CountryDTO countryDTO);
	
	public ResponseEntity<CountryResponseRest> update(CountryDTO countryDTO, Long id);
	
	public ResponseEntity<CountryResponseRest> deleteById(Long id);
	
	public ResponseEntity<CountryResponseRest> loadCountriesFromApi(CountryDTO countryDTO);
}
