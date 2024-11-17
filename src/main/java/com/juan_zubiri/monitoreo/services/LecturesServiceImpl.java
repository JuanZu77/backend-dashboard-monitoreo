package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.LectureRepository;
import com.juan_zubiri.monitoreo.model.Lectures;
import com.juan_zubiri.monitoreo.response.LecturesResponseRest;

@Service
public class LecturesServiceImpl implements ILecturesService{
	
	@Autowired
	private LectureRepository lectureRepository;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LecturesResponseRest> search() {
		
		LecturesResponseRest response = new LecturesResponseRest();
		
		 try {
		    	List<Lectures> plants = lectureRepository.findAll(); 
		        response.getLecturesResponse().setLectures(plants);
		        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LecturesResponseRest> searchById(Long id) {
	

		LecturesResponseRest response = new LecturesResponseRest();
		
		try {
	        Optional<Lectures> lecture = lectureRepository.findById(id);
	        if (lecture.isPresent()) {
	            response.getLecturesResponse().setLectures(List.of(lecture.get()));
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
	public ResponseEntity<LecturesResponseRest> save(Lectures lectures) {
	
		LecturesResponseRest response = new LecturesResponseRest();
		try {
        // guardo la lectura
        Lectures savedLecture = lectureRepository.save(lectures);

        // respuesta
        response.setMetadata("Éxito", "200", "Lectura guardada correctamente");
        response.getLecturesResponse().setLectures(Collections.singletonList(savedLecture));
        return ResponseEntity.ok(response);
		
       } catch (Exception e) {
        response.setMetadata("Error", "500", "Error al guardar la Lectura: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
	 
	}

	@Override
	@Transactional
	public ResponseEntity<LecturesResponseRest> update(Lectures lectures, Long id) {
		
		LecturesResponseRest response = new LecturesResponseRest();
		
		try {
	        Optional<Lectures> lectureOptional = lectureRepository.findById(id);
	        if (lectureOptional.isPresent()) {
	            Lectures lectureToUpdate = lectureOptional.get();
	            lectureToUpdate.setLectureNumber(lectures.getLectureNumber()); 
	           
	            lectureRepository.save(lectureToUpdate);
	            response.getLecturesResponse().setLectures(List.of(lectureToUpdate));
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
	public ResponseEntity<LecturesResponseRest> deleteById(Long id) {
		
		LecturesResponseRest response = new LecturesResponseRest();
		
		 try {
		        Optional<Lectures> lectureOptional = lectureRepository.findById(id);
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
