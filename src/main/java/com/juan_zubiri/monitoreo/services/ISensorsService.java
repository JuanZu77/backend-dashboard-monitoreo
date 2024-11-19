package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.SensorsDTO;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;
import com.juan_zubiri.monitoreo.response.SensorsResponseRest;


public interface ISensorsService {


	public ResponseEntity<SensorsResponseRest> search();
	public ResponseEntity<SensorsResponseRest> searchById(Long id);
	
	public ResponseEntity<SensorsResponseRest> save(SensorsDTO sensorsDTO);
	
	public ResponseEntity<SensorsResponseRest> update(SensorsDTO sensorsDTO, Long id);
	
	public ResponseEntity<SensorsResponseRest> deleteById(Long id);
	
	int countSensorsByType(SensorType sensorType);
}
