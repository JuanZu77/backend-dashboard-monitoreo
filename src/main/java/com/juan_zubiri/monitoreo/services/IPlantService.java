package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.PlantDTO;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;


public interface IPlantService {

    ResponseEntity<PlantResponseRest> search();
    ResponseEntity<PlantResponseRest> searchById(Long id);

    // Cambiar el tipo de entrada
    ResponseEntity<PlantResponseRest> save(PlantDTO plantDTO);

    ResponseEntity<PlantResponseRest> update(PlantDTO plantDTO, Long id);

    ResponseEntity<PlantResponseRest> deleteById(Long id);
}

