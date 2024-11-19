package com.juan_zubiri.monitoreo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.juan_zubiri.monitoreo.dto.PlantDTO;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;
import com.juan_zubiri.monitoreo.services.IPlantService;

@RestController
@RequestMapping("/api")
public class PlantRestController {

    @Autowired
    private IPlantService plantService;

    // Buscar todas las plantas
    @GetMapping("/plant")
    public ResponseEntity<PlantResponseRest> getAllPlants() {
        return plantService.search();
    }

    // Buscar planta por ID
    @GetMapping("/plant/{id}")
    public ResponseEntity<PlantResponseRest> getPlantById(@PathVariable Long id) {
        return plantService.searchById(id);
    }

    // Crear una nueva planta
    @PostMapping("/plant")
    public ResponseEntity<PlantResponseRest> createPlant(@RequestBody PlantDTO plantDTO) {
        return plantService.save(plantDTO);
    }

    // Actualizar planta existente
    @PutMapping("/plant/{id}")
    public ResponseEntity<PlantResponseRest> updatePlant(@RequestBody PlantDTO plantDTO, @PathVariable Long id) {
        return plantService.update(plantDTO, id);
    }

    // Eliminar planta por ID
    @DeleteMapping("/plant/{id}")
    public ResponseEntity<PlantResponseRest> deletePlant(@PathVariable Long id) {
        return plantService.deleteById(id);
    }
}
