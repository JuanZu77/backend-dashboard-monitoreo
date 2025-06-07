package com.juan_zubiri.monitoreo.controller;

import com.juan_zubiri.monitoreo.dto.PasswordUpdateDTO;
import com.juan_zubiri.monitoreo.dto.RegisterUserDTO;
import com.juan_zubiri.monitoreo.dto.UserDTO;
import com.juan_zubiri.monitoreo.response.UserResponseRest;
import com.juan_zubiri.monitoreo.services.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    
    @GetMapping("/users/{id}")
	public ResponseEntity<UserResponseRest> searchById(@PathVariable Long id){
		
		 ResponseEntity<UserResponseRest> response = userService.searchById(id);
		 return response;
	}
    
    @DeleteMapping("/users/{id}")
	public ResponseEntity<UserResponseRest> deleteById(@PathVariable Long id){
		
		 ResponseEntity<UserResponseRest> response = userService.deleteById(id);
		 return response;
	}

    @PutMapping("/users/{id}")
	public ResponseEntity<UserResponseRest> update(@RequestBody UserDTO userDTO, @PathVariable Long id){
		
		 ResponseEntity<UserResponseRest> response = userService.update(userDTO,id);
		 return response;
	}
    
    @PutMapping("users/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO passwordUpdateDTO) {
        try {
    
        	 ResponseEntity<UserResponseRest> response = userService.updatePassword(id, passwordUpdateDTO);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la contraseña.");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<UserResponseRest> registerUser(
            @Valid @RequestBody RegisterUserDTO registerUserDTO,
            HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return userService.registerUser(registerUserDTO, ipAddress);
    }


    @PostMapping("/login")
    public ResponseEntity<UserResponseRest> login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }
    
    @GetMapping("/users/find-by-email")
    public ResponseEntity<UserResponseRest> findByEmail(@RequestParam String email) {
        return userService.searchByEmail(email);
    }
}
