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

import com.juan_zubiri.monitoreo.dto.AlertsDTO;
import com.juan_zubiri.monitoreo.model.Alerts.AlertType;
import com.juan_zubiri.monitoreo.response.AlertsResponseRest;
import com.juan_zubiri.monitoreo.services.IAlertsService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class AlertRestController {

	@Autowired
	private IAlertsService alertService;
	
	@GetMapping("/alerts")
	public ResponseEntity<AlertsResponseRest> search(){
		
		 ResponseEntity<AlertsResponseRest> response = alertService.search();
		 return response;
	}
	 
	 @GetMapping("/alerts/{id}")
	public ResponseEntity<AlertsResponseRest> searchById(@PathVariable Long id){
		
		 ResponseEntity<AlertsResponseRest> response = alertService.searchById(id);
		 return response;
	}
	 
	 @GetMapping("/alerts/total")
	 public ResponseEntity<Integer> getTotalAlerts() {
	     int totalAlerts = alertService.countTotalAlerts();
	     return ResponseEntity.ok(totalAlerts);
	 }

	 @GetMapping("/alerts/count/{alertType}")
	 public ResponseEntity<Integer> getCountByType(@PathVariable AlertType alertType) {
	     int countByType = alertService.countAlertsByType(alertType);
	     return ResponseEntity.ok(countByType);
	 }

	 
	 
	 @PostMapping("/alerts")
	    public ResponseEntity<AlertsResponseRest> save(@RequestBody AlertsDTO alertDTO) {
	        return alertService.save(alertDTO);
	    }
	 
	 
	 @PutMapping("/alerts/{id}")
	public ResponseEntity<AlertsResponseRest> update(@RequestBody AlertsDTO alertDTO, @PathVariable Long id){
		
		 ResponseEntity<AlertsResponseRest> response = alertService.update(alertDTO, id);
		 return response;
	}
	 
	 
	 @DeleteMapping("/alerts/{id}")
	public ResponseEntity<AlertsResponseRest> delete( @PathVariable Long id){
		
		 ResponseEntity<AlertsResponseRest> response = alertService.deleteById(id);
		 return response;
	}

	
}
