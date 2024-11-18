package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.CountryRepository;
import com.juan_zubiri.monitoreo.dao.PlantRepository;
import com.juan_zubiri.monitoreo.dao.UserRepository;
import com.juan_zubiri.monitoreo.dto.RegisterPlantDTO;
import com.juan_zubiri.monitoreo.model.Country;
import com.juan_zubiri.monitoreo.model.Plant;
import com.juan_zubiri.monitoreo.model.User;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;


@Service
public class PlantServiceImpl implements IPlantService{
	
	@Autowired
	private PlantRepository plantRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private UserRepository userRepository;

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
	@Transactional(readOnly = true)
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
	
	@Override
	public ResponseEntity<PlantResponseRest> save(Plant plant) {
	    PlantResponseRest response = new PlantResponseRest();
	    try {
	        // Verifico si el país existe
	        if (plant.getCountry() == null || plant.getCountry().getId() == null) {
	            response.setMetadata("Error", "400", "Country no proporcionado en la solicitud");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }

	        Optional<Country> countryOpt = countryRepository.findById(plant.getCountry().getId());
	        if (!countryOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Country no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        // Verificación de que countryOpt.get() no es null
	        System.out.println("Country encontrado: " + countryOpt.get().getName());
	        
	        // Asignar el país a la planta
	        plant.setCountry(countryOpt.get());
	        System.out.println("Country asignado a planta: " + plant.getCountry().getName());

	        // Verifico si el usuario existe
	        if (plant.getUser() == null || plant.getUser().getId() == null) {
	            response.setMetadata("Error", "400", "Usuario no proporcionado en la solicitud");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }

	        Optional<User> userOpt = userRepository.findById(plant.getUser().getId());
	        if (!userOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Usuario no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        // Asignar el usuario a la planta
	        plant.setUser(userOpt.get());
	        System.out.println("Usuario asignado a planta: " + plant.getUser().getName());

	        // Guardar la planta
	        Plant savedPlant = plantRepository.save(plant);

	        // Respuesta exitosa
	        response.setMetadata("Éxito", "200", "Planta guardada correctamente");
	        response.getPlantResponse().setPlant(Collections.singletonList(savedPlant));
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al guardar la Planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<PlantResponseRest> registerPlant(RegisterPlantDTO registerPlantDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
