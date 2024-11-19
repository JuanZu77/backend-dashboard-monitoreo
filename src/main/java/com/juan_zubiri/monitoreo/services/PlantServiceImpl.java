package com.juan_zubiri.monitoreo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juan_zubiri.monitoreo.dao.CountryRepository;
import com.juan_zubiri.monitoreo.dao.PlantRepository;
import com.juan_zubiri.monitoreo.dao.UserRepository;
import com.juan_zubiri.monitoreo.dto.PlantDTO;
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
	public ResponseEntity<PlantResponseRest> search() {
	    PlantResponseRest response = new PlantResponseRest();

	    try {
	        List<Plant> plantsList = plantRepository.findAll();
	        if (plantsList.isEmpty()) {
	            response.setMetadata("Advertencia", "404", "No se encontraron plantas");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        //  la lista de plantas a DTO
	        List<PlantDTO> plantsDTOList = new ArrayList<>();
	        for (Plant plant : plantsList) {
	            PlantDTO plantDTO = new PlantDTO();
	            plantDTO.setId(plant.getId());
	            plantDTO.setName(plant.getName());
	            plantDTO.setUserId(plant.getUser().getId());
	            plantDTO.setUserName(plant.getUser().getName());
	            plantDTO.setCountryId(plant.getCountry().getId());
	            plantDTO.setCountryName(plant.getCountry().getName());
	            plantsDTOList.add(plantDTO);
	        }

	        response.getPlantResponse().setPlants(plantsDTOList);
	        response.setMetadata("Éxito", "200", "Plantas recuperadas correctamente");

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al recuperar las plantas: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<PlantResponseRest> searchById(Long id) {
	    PlantResponseRest response = new PlantResponseRest();

	    try {
	        Optional<Plant> plantOpt = plantRepository.findById(id);
	        if (!plantOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Planta no encontrada");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        Plant plant = plantOpt.get();

	        PlantDTO plantDTO = new PlantDTO();
	        plantDTO.setId(plant.getId());
	        plantDTO.setName(plant.getName());
	        plantDTO.setUserId(plant.getUser().getId());
	        plantDTO.setUserName(plant.getUser().getName());
	        plantDTO.setCountryId(plant.getCountry().getId());
	        plantDTO.setCountryName(plant.getCountry().getName());

	        response.setMetadata("Éxito", "200", "Planta encontrada");
	        response.getPlantResponse().setPlants(Collections.singletonList(plantDTO));
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al buscar la Planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<PlantResponseRest> save(PlantDTO plantDTO) {
	    PlantResponseRest response = new PlantResponseRest();

	    try {
	    	
	    	if (plantDTO.getCountryId() == null) {
	    	    response.setMetadata("Error", "400", "CountryId no proporcionado");
	    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    	}

	    	if (plantDTO.getUserId() == null) {
	    	    response.setMetadata("Error", "400", "UserId no proporcionado");
	    	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    	}

	        // Verifico que el país existe
	        Optional<Country> countryOpt = countryRepository.findById(plantDTO.getCountryId());
	        if (!countryOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Country no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        // verifico que el usuari
	        Optional<User> userOpt = userRepository.findById(plantDTO.getUserId());
	        if (!userOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Usuario no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        // debo convertir a DTO la entidad
	        Plant plant = new Plant();
	        plant.setName(plantDTO.getName());
	        plant.setCountry(countryOpt.get());
	        plant.setUser(userOpt.get());


	        Plant savedPlant = plantRepository.save(plant);


	        PlantDTO savedPlantDTO = new PlantDTO();
	        savedPlantDTO.setId(savedPlant.getId());
	        savedPlantDTO.setName(savedPlant.getName());
	        savedPlantDTO.setUserId(savedPlant.getUser().getId());
	        savedPlantDTO.setUserName(savedPlant.getUser().getName());
	        savedPlantDTO.setCountryId(savedPlant.getCountry().getId());
	        savedPlantDTO.setCountryName(savedPlant.getCountry().getName());

	        response.setMetadata("Éxito", "200", "Planta guardada correctamente");
	        response.getPlantResponse().setPlants(Collections.singletonList(savedPlantDTO));
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al guardar la Planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<PlantResponseRest> update(PlantDTO plantDTO, Long id) {
	    PlantResponseRest response = new PlantResponseRest();

	    try {
	        Optional<Plant> plantOpt = plantRepository.findById(id); 
	        if (!plantOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Planta no encontrada");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        Plant plant = plantOpt.get();

	        //  actualizar campos de la planta
	        if (plantDTO.getName() != null) {
	            plant.setName(plantDTO.getName());
	        }

	        // si pais exite
	        if (plantDTO.getCountryId() != null) {
	            Optional<Country> countryOpt = countryRepository.findById(plantDTO.getCountryId());
	            if (!countryOpt.isPresent()) {
	                response.setMetadata("Error", "404", "Country no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	            plant.setCountry(countryOpt.get());
	        }

	        // verifico si el usuario existe
	        if (plantDTO.getUserId() != null) {
	            Optional<User> userOpt = userRepository.findById(plantDTO.getUserId());
	            if (!userOpt.isPresent()) {
	                response.setMetadata("Error", "404", "Usuario no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	            plant.setUser(userOpt.get());
	        }

	        
	        Plant updatedPlant = plantRepository.save(plant);

	        PlantDTO updatedPlantDTO = new PlantDTO();
	        updatedPlantDTO.setId(updatedPlant.getId());
	        updatedPlantDTO.setName(updatedPlant.getName());
	        updatedPlantDTO.setUserId(updatedPlant.getUser().getId());
	        updatedPlantDTO.setUserName(updatedPlant.getUser().getName());
	        updatedPlantDTO.setCountryId(updatedPlant.getCountry().getId());
	        updatedPlantDTO.setCountryName(updatedPlant.getCountry().getName());

	        response.getPlantResponse().setPlants(Collections.singletonList(updatedPlantDTO));
	        response.setMetadata("Éxito", "200", "Planta actualizada correctamente");

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al actualizar la planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<PlantResponseRest> deleteById(Long id) {
	    PlantResponseRest response = new PlantResponseRest();

	    try {
	        Optional<Plant> plantOpt = plantRepository.findById(id); 
	        if (!plantOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Planta no encontrada");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        plantRepository.deleteById(id); 

	        response.setMetadata("Éxito", "200", "Planta eliminada correctamente");
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al eliminar la planta: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	

	

}
