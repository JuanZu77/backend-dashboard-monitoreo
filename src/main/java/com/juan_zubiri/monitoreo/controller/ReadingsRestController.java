package com.juan_zubiri.monitoreo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.juan_zubiri.monitoreo.dto.ReadingsDTO;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;
import com.juan_zubiri.monitoreo.services.IReadingsService;

@RestController
@RequestMapping("/api")
public class ReadingsRestController {

    @Autowired
    private IReadingsService readingsService;

    // Buscar todas las lecturas
    @GetMapping("/readings")
    public ResponseEntity<ReadingsResponseRest> search() {
        ResponseEntity<ReadingsResponseRest> response = readingsService.search();
        return response;
    }

    // Buscar una lectura por su ID
    @GetMapping("/readings/{id}")
    public ResponseEntity<ReadingsResponseRest> searchById(@PathVariable Long id) {
        ResponseEntity<ReadingsResponseRest> response = readingsService.searchById(id);
        return response;
    }

    // Guardar una nueva lectura (usando DTO)
    @PostMapping("/readings")
    public ResponseEntity<ReadingsResponseRest> save(@RequestBody ReadingsDTO readingsDTO) {
        ResponseEntity<ReadingsResponseRest> response = readingsService.save(readingsDTO);
        return response;
    }

    // Actualizar una lectura existente (usando DTO)
    @PutMapping("/readings/{id}")
    public ResponseEntity<ReadingsResponseRest> update(@RequestBody ReadingsDTO readingsDTO, @PathVariable Long id) {
        ResponseEntity<ReadingsResponseRest> response = readingsService.update(readingsDTO, id);
        return response;
    }

    // Eliminar una lectura por su ID
    @DeleteMapping("/readings/{id}")
    public ResponseEntity<ReadingsResponseRest> delete(@PathVariable Long id) {
        ResponseEntity<ReadingsResponseRest> response = readingsService.deleteById(id);
        return response;
    }
}

