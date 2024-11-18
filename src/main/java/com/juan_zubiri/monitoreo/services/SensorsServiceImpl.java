package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.juan_zubiri.monitoreo.dao.SensorsRepository;
import com.juan_zubiri.monitoreo.model.Sensors;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;
import com.juan_zubiri.monitoreo.response.SensorsResponseRest;

@Service
public class SensorsServiceImpl implements ISensorsService{
	
	@Autowired
	private SensorsRepository sensorsRepository;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<SensorsResponseRest> search() {
		
		SensorsResponseRest response = new SensorsResponseRest();
		
		try {
	    	List<Sensors> sensors = sensorsRepository.findAll(); 
	        response.getSensorsResponse().setSensors(sensors);
	        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<SensorsResponseRest> searchById(Long id) {
		
		SensorsResponseRest response = new SensorsResponseRest();
		
		try {
	        Optional<Sensors> sensors = sensorsRepository.findById(id);
	        if (sensors.isPresent()) {
	            response.getSensorsResponse().setSensors(List.of(sensors.get()));
	            response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al consultar", "-1", "Sensor no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	public ResponseEntity<SensorsResponseRest> save(Sensors sensors) {
		
		SensorsResponseRest response = new SensorsResponseRest();
		
		try {
	        // guardo el sensor
	        Sensors savedAlert = sensorsRepository.save(sensors);

	        // respuesta
	        response.setMetadata("Éxito", "200", "Sensor guardado correctamente");
	        response.getSensorsResponse().setSensors(Collections.singletonList(savedAlert));
	        return ResponseEntity.ok(response);
			
	       } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al guardar las Sensores: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	       }
	}

	@Override
	@Transactional
	public ResponseEntity<SensorsResponseRest> update(Sensors sensors, Long id) {
		
		SensorsResponseRest response = new SensorsResponseRest();
		
		try {
	        Optional<Sensors> sensorsOptional = sensorsRepository.findById(id);
	        if (sensorsOptional .isPresent()) {
	            Sensors sensorsToUpdate = sensorsOptional .get();
	            sensorsToUpdate.setSensorType(sensors.getSensorType()); 
	           
	            sensorsRepository.save(sensorsToUpdate);
	            response.getSensorsResponse().setSensors(List.of(sensorsToUpdate));
	            response.setMetadata("Respuesta exitosa", "00", "Sensor actualizado correctamente");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al actualizar", "-1", "Sensor no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al actualizar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<SensorsResponseRest> deleteById(Long id) {
		
		SensorsResponseRest response = new SensorsResponseRest();
		
		try {
	        Optional<Sensors> sensorsOptional = sensorsRepository.findById(id);
	        if (sensorsOptional.isPresent()) {
	            sensorsRepository.deleteById(id);
	            response.setMetadata("Respuesta exitosa", "00", "Sensor eliminado correctamente");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al eliminar", "-1", "Sensor no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al eliminar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	
	@Override
	public int countSensorsByType(SensorType sensorType) {
		
	    return sensorsRepository.countBySensorType(sensorType);
	}


}
