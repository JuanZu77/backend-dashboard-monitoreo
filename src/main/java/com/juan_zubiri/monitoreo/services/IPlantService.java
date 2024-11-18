package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.RegisterPlantDTO;
import com.juan_zubiri.monitoreo.model.Plant;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;


public interface IPlantService {

	public ResponseEntity<PlantResponseRest> search();
	public ResponseEntity<PlantResponseRest> searchById(Long id);
	
	public ResponseEntity<PlantResponseRest> save(Plant plant);
	
	public ResponseEntity<PlantResponseRest> update(Plant plant, Long id);
	
	public ResponseEntity<PlantResponseRest> deleteById(Long id);
	
	public interface PlantService {
	    ResponseEntity<PlantResponseRest> registerPlant(RegisterPlantDTO registerPlantDTO);
	}

	ResponseEntity<PlantResponseRest> registerPlant(RegisterPlantDTO registerPlantDTO);

}
