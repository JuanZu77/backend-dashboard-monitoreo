package com.juan_zubiri.monitoreo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;
import com.juan_zubiri.monitoreo.services.IReadingsService;

@RestController
@RequestMapping("/api")
public class ReadingsRestController {
	
	@Autowired
	private IReadingsService readingsService;
	
	@GetMapping("/readings")
	public ResponseEntity<ReadingsResponseRest> search(){
		
		 ResponseEntity<ReadingsResponseRest> response = readingsService.search();
		 return response;
	}
	 
	 @GetMapping("/readings/{id}")
	public ResponseEntity<ReadingsResponseRest> searchById(@PathVariable Long id){
		
		 ResponseEntity<ReadingsResponseRest> response = readingsService.searchById(id);
		 return response;
	}
	 
	 
	 @PostMapping("/readings")
		public ResponseEntity<ReadingsResponseRest> save(@RequestBody Readings readings){
			
			 ResponseEntity<ReadingsResponseRest> response = readingsService.save(readings);
			 return response;
		} 
	 
	 
	 @PutMapping("/readings/{id}")
	public ResponseEntity<ReadingsResponseRest> update(@RequestBody Readings readings, @PathVariable Long id){
		
		 ResponseEntity<ReadingsResponseRest> response = readingsService.update(readings, id);
		 return response;
	}
	 
	 
	 @DeleteMapping("/readings/{id}")
	public ResponseEntity<ReadingsResponseRest> delete( @PathVariable Long id){
		
		 ResponseEntity<ReadingsResponseRest> response = readingsService.deleteById(id);
		 return response;
	}


}
