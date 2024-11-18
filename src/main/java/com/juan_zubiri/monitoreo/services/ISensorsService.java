package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;
import com.juan_zubiri.monitoreo.model.Sensors;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;
import com.juan_zubiri.monitoreo.response.SensorsResponseRest;


public interface ISensorsService {


	public ResponseEntity<SensorsResponseRest> search();
	public ResponseEntity<SensorsResponseRest> searchById(Long id);
	
	public ResponseEntity<SensorsResponseRest> save(Sensors sensors);
	
	public ResponseEntity<SensorsResponseRest> update(Sensors sensors, Long id);
	
	public ResponseEntity<SensorsResponseRest> deleteById(Long id);
	
	int countSensorsByType(SensorType sensorType);
}
