package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;

import com.juan_zubiri.monitoreo.dto.RegisterUserDTO;
import com.juan_zubiri.monitoreo.dto.UserDTO;
import com.juan_zubiri.monitoreo.response.UserResponseRest;


public interface IUserService {
	public ResponseEntity<UserResponseRest> search();
	public ResponseEntity<UserResponseRest> searchById(Long id);

	public ResponseEntity<UserResponseRest> update(UserDTO userDTO, Long id);
	public ResponseEntity<UserResponseRest> deleteById(Long id);
	
	 public ResponseEntity<UserResponseRest> registerUser(RegisterUserDTO registerUserDTO, String ipAddress);
	 
	 public ResponseEntity<UserResponseRest> login(String email, String password);

}
