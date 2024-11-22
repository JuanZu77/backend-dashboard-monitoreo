package com.juan_zubiri.monitoreo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan_zubiri.monitoreo.dto.SensorsDTO;
import com.juan_zubiri.monitoreo.model.Sensors.SensorType;
import com.juan_zubiri.monitoreo.response.SensorsResponseRest;
import com.juan_zubiri.monitoreo.services.ISensorsService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class SensorRestController {

	@Autowired
	private ISensorsService sensorsService;
	
	@GetMapping("/sensors")
	public ResponseEntity<SensorsResponseRest> search(){
		
		 ResponseEntity<SensorsResponseRest> response = sensorsService.search();
		 return response;
	}
	 
	 @GetMapping("/sensors/{id}")
	public ResponseEntity<SensorsResponseRest> searchById(@PathVariable Long id){
		
		 ResponseEntity<SensorsResponseRest> response = sensorsService.searchById(id);
		 return response;
	}
	 

	 @GetMapping("/sensors/count/{sensorType}")
	 public ResponseEntity<Integer> getCountByType(@PathVariable SensorType sensorType) {
	     int countByType = sensorsService.countSensorsByType(sensorType);
	     return ResponseEntity.ok(countByType);
	 }

	 
	 
	 @PostMapping("/sensors")
		public ResponseEntity<SensorsResponseRest> save(@RequestBody SensorsDTO sensorsDTO){
			
			 ResponseEntity<SensorsResponseRest> response = sensorsService.save(sensorsDTO);
			 return response;
		} 
	 
	 
	 @PutMapping("/sensors/{id}")
	public ResponseEntity<SensorsResponseRest> update(@RequestBody SensorsDTO sensorsDTO, @PathVariable Long id){
		
		 ResponseEntity<SensorsResponseRest> response = sensorsService.update(sensorsDTO, id);
		 return response;
	}
	 
	 
	 @DeleteMapping("/sensors/{id}")
	public ResponseEntity<SensorsResponseRest> delete( @PathVariable Long id){
		
		 ResponseEntity<SensorsResponseRest> response = sensorsService.deleteById(id);
		 return response;
	}

}
