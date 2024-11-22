package com.juan_zubiri.monitoreo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juan_zubiri.monitoreo.model.Sensors;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;

@Repository
public interface SensorsRepository extends JpaRepository<Sensors, Long>{
	
	int countBySensorType(SensorType sensorType);

}
