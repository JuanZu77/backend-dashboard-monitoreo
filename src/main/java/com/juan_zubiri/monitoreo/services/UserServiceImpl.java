package com.juan_zubiri.monitoreo.services;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import com.juan_zubiri.monitoreo.dao.RegisterUserRepository;
import com.juan_zubiri.monitoreo.dao.UserRepository;
import com.juan_zubiri.monitoreo.dto.RegisterUserDTO;
//import com.juan_zubiri.monitoreo.model.RegisterUser;
import com.juan_zubiri.monitoreo.model.User;
import com.juan_zubiri.monitoreo.response.UserResponseRest;
import com.juan_zubiri.monitoreo.util.JwtUtil;

@Service
public class UserServiceImpl implements IUserService{
	
	   @Autowired
	    private UserRepository userRepository;

//	    @Autowired
//	    private RegisterUserRepository registerUserRepository;
	    
	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private JwtUtil jwtUtil;


	@Override
	public ResponseEntity<UserResponseRest> search() {
		
		UserResponseRest response = new UserResponseRest();
	    
		try {
	    	List<User> sensors = userRepository.findAll(); 
	        response.getUserResponse().setUsers(sensors);
	        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	public ResponseEntity<UserResponseRest> searchById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UserResponseRest> save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UserResponseRest> update(User user, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<UserResponseRest> deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ResponseEntity<UserResponseRest> registerUser(RegisterUserDTO registerUserDTO, String ipAddress) {
	    UserResponseRest response = new UserResponseRest();

	    try {
	        // Validación de correo
	        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
	            response.setMessage("El correo ya está registrado");
	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	        }

	        // Crear y guardar el usuario
	        User user = new User();
	        user.setEmail(registerUserDTO.getEmail());
	        user.setName(registerUserDTO.getName());
	        user.setLastName(registerUserDTO.getLastName());
	        user.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));

	        userRepository.save(user);

	        response.setMessage("Usuario registrado exitosamente");
	        response.setUser(user);

	        return new ResponseEntity<>(response, HttpStatus.CREATED);

	    } catch (Exception e) {
	        e.printStackTrace(); // Para depuración
	        response.setMessage("Error al registrar el usuario: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@Override
	public ResponseEntity<UserResponseRest> login(String email, String password) {
		
		  UserResponseRest response = new UserResponseRest();
		    try {
		        // valido correo
		        Optional<User> userOptional = userRepository.findByEmail(email);
		        if (!userOptional.isPresent()) {
		            response.setMessage("Correo o contraseña incorrectos");
		            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		        }

		        // obtengo usuaio
		        User user = userOptional.get();

		        // verifico la contraseña
		        if (!passwordEncoder.matches(password, user.getPassword())) {
		            response.setMessage("Correo o contraseña incorrectos");
		            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		        }
		        
		     // Genero el token JWT
		        String token = jwtUtil.generateToken(user);


		        response.setMessage("Login exitoso");
		        response.setUser(user); //agrego la info del usuario, puede ser util 
		        response.setToken(token);
		        return new ResponseEntity<>(response, HttpStatus.OK);

		    } catch (Exception e) {
		        response.setMessage("Error al intentar autenticar al usuario: " + e.getMessage());
		        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

}
