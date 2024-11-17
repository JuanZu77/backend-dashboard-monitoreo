package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.PlantRepository;
import com.juan_zubiri.monitoreo.model.Plant;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;


@Service
public class PlantServiceImpl implements IPlantService{
	
	@Autowired
	private PlantRepository plantRepository;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<PlantResponseRest> search() {
		
		 PlantResponseRest response = new PlantResponseRest();
		    try {
		    	List<Plant> plants = plantRepository.findAll(); 
		        response.getPlantResponse().setPlant(plants);
		        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		 
	}

	@Override
	public ResponseEntity<PlantResponseRest> searchById(Long id) {
		 
		PlantResponseRest response = new PlantResponseRest();
		    try {
		        Optional<Plant> plant = plantRepository.findById(id);
		        if (plant.isPresent()) {
		            response.getPlantResponse().setPlant(List.of(plant.get()));
		            response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al consultar", "-1", "Planta no encontrado");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	public ResponseEntity<PlantResponseRest> save(Plant plant) {
		
		PlantResponseRest response = new PlantResponseRest();
		try {
	        // verifico si plant existe
	        Optional<Plant> existingPlant = plantRepository.findByName(plant.getName());
	        if (existingPlant.isPresent()) {
	            response.setMetadata("Error", "409", "La Planta ya existe en la base de datos");
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	        }

	        // guardo la Planta
	        Plant savedCountry = plantRepository.save(plant);

	        // respuesta
	        response.setMetadata("Éxito", "200", "Planta guardado correctamente");
	        response.getPlantResponse().setPlant(Collections.singletonList(savedCountry));
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al guardar la Planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
		 
	}

	@Override
	public ResponseEntity<PlantResponseRest> update(Plant plant, Long id) {
		
		PlantResponseRest response = new PlantResponseRest();
		
		 try {
		        Optional<Plant> plantOptional = plantRepository.findById(id);
		        if (plantOptional.isPresent()) {
		            Plant plantToUpdate = plantOptional.get();
		            plantToUpdate.setName(plant.getName()); 
		           
		            plantRepository.save(plantToUpdate);
		            response.getPlantResponse().setPlant(List.of(plantToUpdate));
		            response.setMetadata("Respuesta exitosa", "00", "Planta actualizada correctamente");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al actualizar", "-1", "Planta no encontrado");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al actualizar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		
	}

	@Override
	public ResponseEntity<PlantResponseRest> deleteById(Long id) {
		
		PlantResponseRest response = new PlantResponseRest();
		
		 try {
		        Optional<Plant> plantOptional = plantRepository.findById(id);
		        if (plantOptional.isPresent()) {
		            plantRepository.deleteById(id);
		            response.setMetadata("Respuesta exitosa", "00", "Planta eliminada correctamente");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al eliminar", "-1", "Planta no encontrada");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al eliminar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
		
	}
	

}
