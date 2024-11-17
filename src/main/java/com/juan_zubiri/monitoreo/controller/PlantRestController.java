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

import com.juan_zubiri.monitoreo.model.Plant;
import com.juan_zubiri.monitoreo.response.PlantResponseRest;
import com.juan_zubiri.monitoreo.services.IPlantService;

@RestController
@RequestMapping("/api")
public class PlantRestController {
	
	@Autowired
	private IPlantService plantService;
	
	 @GetMapping("/plant")
		public ResponseEntity<PlantResponseRest> search(){
			
			 ResponseEntity<PlantResponseRest> response = plantService.search();
			 return response;
		}
		 
		 @GetMapping("/plant/{id}")
		public ResponseEntity<PlantResponseRest> searchById(@PathVariable Long id){
			
			 ResponseEntity<PlantResponseRest> response = plantService.searchById(id);
			 return response;
		}
		 
		 
		 @PostMapping("/plant")
			public ResponseEntity<PlantResponseRest> save(@RequestBody Plant plant){
				
				 ResponseEntity<PlantResponseRest> response = plantService.save(plant);
				 return response;
			} 
		 
		 
		 @PutMapping("/plant/{id}")
		public ResponseEntity<PlantResponseRest> update(@RequestBody Plant plant, @PathVariable Long id){
			
			 ResponseEntity<PlantResponseRest> response = plantService.update(plant, id);
			 return response;
		}
		 
		 
		 @DeleteMapping("/plant/{id}")
		public ResponseEntity<PlantResponseRest> delete( @PathVariable Long id){
			
			 ResponseEntity<PlantResponseRest> response = plantService.deleteById(id);
			 return response;
		}

}
