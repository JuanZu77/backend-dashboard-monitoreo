package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.ReadingsDTO;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;

public interface IReadingsService {

    public ResponseEntity<ReadingsResponseRest> search();
    public ResponseEntity<ReadingsResponseRest> searchById(Long id);

    public ResponseEntity<ReadingsResponseRest> save(ReadingsDTO readingsDTO);  // Usar DTO en lugar de entidad
    public ResponseEntity<ReadingsResponseRest> update(ReadingsDTO readingsDTO, Long id);  // Usar DTO

    public ResponseEntity<ReadingsResponseRest> deleteById(Long id);
}
