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

import com.juan_zubiri.monitoreo.model.Lectures;
import com.juan_zubiri.monitoreo.response.LecturesResponseRest;
import com.juan_zubiri.monitoreo.services.ILecturesService;

@RestController
@RequestMapping("/api")
public class LecturesRestController {
	
	@Autowired
	private ILecturesService lecturesService;
	
	@GetMapping("/lectures")
	public ResponseEntity<LecturesResponseRest> search(){
		
		 ResponseEntity<LecturesResponseRest> response = lecturesService.search();
		 return response;
	}
	 
	 @GetMapping("/lectures/{id}")
	public ResponseEntity<LecturesResponseRest> searchById(@PathVariable Long id){
		
		 ResponseEntity<LecturesResponseRest> response = lecturesService.searchById(id);
		 return response;
	}
	 
	 
	 @PostMapping("/lectures")
		public ResponseEntity<LecturesResponseRest> save(@RequestBody Lectures lecture){
			
			 ResponseEntity<LecturesResponseRest> response = lecturesService.save(lecture);
			 return response;
		} 
	 
	 
	 @PutMapping("/lectures/{id}")
	public ResponseEntity<LecturesResponseRest> update(@RequestBody Lectures lecture, @PathVariable Long id){
		
		 ResponseEntity<LecturesResponseRest> response = lecturesService.update(lecture, id);
		 return response;
	}
	 
	 
	 @DeleteMapping("/lectures/{id}")
	public ResponseEntity<LecturesResponseRest> delete( @PathVariable Long id){
		
		 ResponseEntity<LecturesResponseRest> response = lecturesService.deleteById(id);
		 return response;
	}


}
