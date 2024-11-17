package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.model.User;
import com.juan_zubiri.monitoreo.response.UserResponseRest;


public interface IUserService {
	public ResponseEntity<UserResponseRest> search();
	public ResponseEntity<UserResponseRest> searchById(Long id);
	
	public ResponseEntity<UserResponseRest> save(User user);
	
	public ResponseEntity<UserResponseRest> update(User user, Long id);
	
	public ResponseEntity<UserResponseRest> deleteById(Long id);
}
