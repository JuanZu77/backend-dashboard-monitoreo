package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.ReadingsRepository;
import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;

@Service
public class ReadingsServiceImpl implements IReadingsService{
	
	@Autowired
	private ReadingsRepository lectureRepository;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ReadingsResponseRest> search() {
		
		ReadingsResponseRest response = new ReadingsResponseRest();
		
		 try {
		    	List<Readings> plants = lectureRepository.findAll(); 
		        response.getReadingsResponse().setReadings(plants);
		        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ReadingsResponseRest> searchById(Long id) {
	

		ReadingsResponseRest response = new ReadingsResponseRest();
		
		try {
	        Optional<Readings> lecture = lectureRepository.findById(id);
	        if (lecture.isPresent()) {
	            response.getReadingsResponse().setReadings(List.of(lecture.get()));
	            response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al consultar", "-1", "Lectura no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<ReadingsResponseRest> save(Readings readings) {
	
		ReadingsResponseRest response = new ReadingsResponseRest();
		try {
        // guardo la lectura
        Readings savedLecture = lectureRepository.save(readings);

        // respuesta
        response.setMetadata("Éxito", "200", "Lectura guardada correctamente");
        response.getReadingsResponse().setReadings(Collections.singletonList(savedLecture));
        return ResponseEntity.ok(response);
		
       } catch (Exception e) {
        response.setMetadata("Error", "500", "Error al guardar la Lectura: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
	 
	}

	@Override
	@Transactional
	public ResponseEntity<ReadingsResponseRest> update(Readings readings, Long id) {
		
		ReadingsResponseRest response = new ReadingsResponseRest();
		
		try {
	        Optional<Readings> lectureOptional = lectureRepository.findById(id);
	        if (lectureOptional.isPresent()) {
	            Readings lectureToUpdate = lectureOptional.get();
	            lectureToUpdate.setReadings_number(readings.getReadings_number()); 
	           
	            lectureRepository.save(lectureToUpdate);
	            response.getReadingsResponse().setReadings(List.of(lectureToUpdate));
	            response.setMetadata("Respuesta exitosa", "00", "Lectura actualizada correctamente");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al actualizar", "-1", "Lectura no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al actualizar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	
	}

	@Override
	@Transactional
	public ResponseEntity<ReadingsResponseRest> deleteById(Long id) {
		
		ReadingsResponseRest response = new ReadingsResponseRest();
		
		 try {
		        Optional<Readings> lectureOptional = lectureRepository.findById(id);
		        if (lectureOptional.isPresent()) {
		            lectureRepository.deleteById(id);
		            response.setMetadata("Respuesta exitosa", "00", "Lectura eliminada correctamente");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al eliminar", "-1", "Lectura no encontrada");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al eliminar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

}
