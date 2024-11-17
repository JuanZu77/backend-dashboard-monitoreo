package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.model.Lectures;
import com.juan_zubiri.monitoreo.response.LecturesResponseRest;


public interface ILecturesService {


	public ResponseEntity<LecturesResponseRest> search();
	public ResponseEntity<LecturesResponseRest> searchById(Long id);
	
	public ResponseEntity<LecturesResponseRest> save(Lectures lectures);
	
	public ResponseEntity<LecturesResponseRest> update(Lectures lectures, Long id);
	
	public ResponseEntity<LecturesResponseRest> deleteById(Long id);
}
