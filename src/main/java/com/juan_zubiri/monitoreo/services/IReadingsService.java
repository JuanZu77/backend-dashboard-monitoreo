package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;


public interface IReadingsService {


	public ResponseEntity<ReadingsResponseRest> search();
	public ResponseEntity<ReadingsResponseRest> searchById(Long id);
	
	public ResponseEntity<ReadingsResponseRest> save(Readings readings);
	
	public ResponseEntity<ReadingsResponseRest> update(Readings readings, Long id);
	
	public ResponseEntity<ReadingsResponseRest> deleteById(Long id);
}
