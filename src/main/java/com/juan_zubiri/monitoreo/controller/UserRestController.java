package com.juan_zubiri.monitoreo.controller;

import com.juan_zubiri.monitoreo.dto.RegisterUserDTO;
import com.juan_zubiri.monitoreo.response.UserResponseRest;
import com.juan_zubiri.monitoreo.services.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private IUserService userService;
    
    
    @GetMapping("/users")
	public ResponseEntity<UserResponseRest> search(){
		
		 ResponseEntity<UserResponseRest> response = userService.search();
		 return response;
	}

    // Endpoint para registrar un usuario
    @PostMapping("/register")
    public ResponseEntity<UserResponseRest> registerUser(
            @Valid @RequestBody RegisterUserDTO registerUserDTO,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return userService.registerUser(registerUserDTO, ipAddress);
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<UserResponseRest> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }
}