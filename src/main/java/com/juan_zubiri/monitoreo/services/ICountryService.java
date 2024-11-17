package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;
import com.juan_zubiri.monitoreo.model.Country;
import com.juan_zubiri.monitoreo.response.CountryResponseRest;

public interface ICountryService {

	public ResponseEntity<CountryResponseRest> search();
	public ResponseEntity<CountryResponseRest> searchById(Long id);
	
	public ResponseEntity<CountryResponseRest> save(Country country);
	
	public ResponseEntity<CountryResponseRest> update(Country country, Long id);
	
	public ResponseEntity<CountryResponseRest> deleteById(Long id);
}
