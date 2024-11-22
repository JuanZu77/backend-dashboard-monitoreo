package com.juan_zubiri.monitoreo.services;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.ReadingsRepository;
import com.juan_zubiri.monitoreo.dao.SensorsRepository;
import com.juan_zubiri.monitoreo.dto.SensorsDTO;
import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.model.Sensors;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;
import com.juan_zubiri.monitoreo.response.SensorsResponseRest;

@Service
public class SensorsServiceImpl implements ISensorsService{
	
	@Autowired
	private SensorsRepository sensorsRepository;
	
	@Autowired
	private ReadingsRepository readingsRepository;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<SensorsResponseRest> search() {
	    SensorsResponseRest response = new SensorsResponseRest();

	    try {

	        List<Sensors> sensorsList = sensorsRepository.findAll();


	        List<SensorsDTO> sensorsDTOList = new ArrayList<>();
	        for (Sensors sensor : sensorsList) {
	            SensorsDTO dto = new SensorsDTO();
	            dto.setId(sensor.getId());
	            dto.setReadingsId(sensor.getReadings().getId());
	            dto.setSensorName(sensor.getSensorName());
	            dto.setReason(sensor.getReason());
	            dto.setSensorType(sensor.getSensorType().name());
	            sensorsDTOList.add(dto);
	        }


	        response.getSensorsResponse().setSensors(sensorsDTOList);


	        response.setMetadata("Éxito", "200", LocalDateTime.now().toString());

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error", "500", LocalDateTime.now().toString());
	        response.setMessage("Error al obtener los sensores: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}



	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<SensorsResponseRest> searchById(Long id) {
	    SensorsResponseRest response = new SensorsResponseRest();

	    try {

	        Optional<Sensors> optionalSensor = sensorsRepository.findById(id);

	        if (optionalSensor.isPresent()) {
	            Sensors sensor = optionalSensor.get();


	            SensorsDTO dto = new SensorsDTO();
	            dto.setId(sensor.getId());
	            dto.setReadingsId(sensor.getReadings().getId());
	            dto.setSensorName(sensor.getSensorName());
	            dto.setReason(sensor.getReason());
	            dto.setSensorType(sensor.getSensorType().name());

	 
	            response.getSensorsResponse().setSensors(Collections.singletonList(dto));

	  
	            response.setMetadata("Éxito", "200", LocalDateTime.now().toString());
	        } else {
	            response.setMetadata("Error", "404", LocalDateTime.now().toString());
	            response.setMessage("Sensor no encontrado con ID: " + id);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error", "500", LocalDateTime.now().toString());
	        response.setMessage("Error al obtener el sensor: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<SensorsResponseRest> save(SensorsDTO sensorsDTO) {
	    SensorsResponseRest response = new SensorsResponseRest();

	    try {
	     
	        Optional<Readings> readingsOpt = readingsRepository.findById(sensorsDTO.getReadingsId());
	        if (!readingsOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Reading no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	
	        Sensors sensor = new Sensors();
	        Readings readings = readingsOpt.get();  
	        sensor.setReadings(readings); 


	        sensor.setSensorName(sensorsDTO.getSensorName());
	        sensor.setReason(sensorsDTO.getReason());
	        sensor.setSensorType(SensorType.valueOf(sensorsDTO.getSensorType())); 


	        Sensors savedSensor = sensorsRepository.save(sensor);

	   
	        SensorsDTO responseSensorDTO = new SensorsDTO();
	        responseSensorDTO.setId(savedSensor.getId());
	        responseSensorDTO.setSensorName(savedSensor.getSensorName());
	        responseSensorDTO.setReason(savedSensor.getReason());
	        responseSensorDTO.setSensorType(savedSensor.getSensorType().name()); 
	        responseSensorDTO.setReadingsId(savedSensor.getReadings().getId());


	        response.getSensorsResponse().setSensors(Collections.singletonList(responseSensorDTO));


	        response.setMetadata("Éxito", "200", "Sensor guardado correctamente");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	    
	        response.setMetadata("Error", "500", "Error al guardar el Sensor: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<SensorsResponseRest> update(SensorsDTO sensorsDTO, Long id) {
	    SensorsResponseRest response = new SensorsResponseRest();

	    try {
	
	        Optional<Sensors> sensorOpt = sensorsRepository.findById(id);
	        if (!sensorOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Sensor no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        Sensors sensor = sensorOpt.get();
	        
	
	        if (sensorsDTO.getSensorName() != null) {
	            sensor.setSensorName(sensorsDTO.getSensorName());
	        }
	        if (sensorsDTO.getReason() != null) {
	            sensor.setReason(sensorsDTO.getReason());
	        }
	        if (sensorsDTO.getSensorType() != null) {
	            sensor.setSensorType(SensorType.valueOf(sensorsDTO.getSensorType()));
	        }

	      
	        if (sensorsDTO.getReadingsId() != null) {
	            Optional<Readings> readingsOpt = readingsRepository.findById(sensorsDTO.getReadingsId());
	            if (readingsOpt.isPresent()) {
	                sensor.setReadings(readingsOpt.get());
	            } else {
	                response.setMetadata("Error", "404", "Reading no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	        }

	  
	        Sensors updatedSensor = sensorsRepository.save(sensor);

	  
	        SensorsDTO responseSensorDTO = new SensorsDTO();
	        responseSensorDTO.setId(updatedSensor.getId());
	        responseSensorDTO.setSensorName(updatedSensor.getSensorName());
	        responseSensorDTO.setReason(updatedSensor.getReason());
	        responseSensorDTO.setSensorType(updatedSensor.getSensorType().name());
	        responseSensorDTO.setReadingsId(updatedSensor.getReadings().getId());

	        response.getSensorsResponse().setSensors(Collections.singletonList(responseSensorDTO));
	        response.setMetadata("Éxito", "200", "Sensor actualizado correctamente");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al actualizar el Sensor: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}



	@Override
	public ResponseEntity<SensorsResponseRest> deleteById(Long id) {
	    SensorsResponseRest response = new SensorsResponseRest();

	    try {
	     
	        Optional<Sensors> sensorOpt = sensorsRepository.findById(id);
	        if (!sensorOpt.isPresent()) {
	            response.setMetadata("Error", "404", "Sensor no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }


	        sensorsRepository.deleteById(id);

	        response.setMetadata("Éxito", "200", "Sensor eliminado correctamente");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error", "500", "Error al eliminar el Sensor: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	
	
	@Override
	public int countSensorsByType(SensorType sensorType) {
		
	    return sensorsRepository.countBySensorType(sensorType);
	}





}
