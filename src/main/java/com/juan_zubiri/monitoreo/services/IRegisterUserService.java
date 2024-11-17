package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;
import com.juan_zubiri.monitoreo.model.RegisterUser;
import com.juan_zubiri.monitoreo.response.RegisterUserResponseRest;


public interface IRegisterUserService {

	public ResponseEntity<RegisterUserResponseRest> search();
	public ResponseEntity<RegisterUserResponseRest> searchById(Long id);
	
	public ResponseEntity<RegisterUserResponseRest> save(RegisterUser registerUser);
	
//	public ResponseEntity<RegisterUserResponseRest> update(RegisterUser registerUser, Long id);
//	
//	public ResponseEntity<RegisterUserResponseRest> deleteById(Long id);
}
