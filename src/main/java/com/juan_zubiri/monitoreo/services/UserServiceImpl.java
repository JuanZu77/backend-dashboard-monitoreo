package com.juan_zubiri.monitoreo.services;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import com.juan_zubiri.monitoreo.dao.RegisterUserRepository;
import com.juan_zubiri.monitoreo.dao.UserRepository;
import com.juan_zubiri.monitoreo.dto.PasswordUpdateDTO;
import com.juan_zubiri.monitoreo.dto.RegisterUserDTO;
import com.juan_zubiri.monitoreo.dto.UserDTO;
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
	            List<User> users = userRepository.findAll(); 
	            List<UserDTO> userDTOs = users.stream().map(user -> {
	                UserDTO userDTO = new UserDTO();
	                userDTO.setId(user.getId());
	                userDTO.setEmail(user.getEmail());
	                userDTO.setName(user.getName());
	                userDTO.setLastName(user.getLastName());
	                return userDTO;
	            }).collect(Collectors.toList());

	            response.setUser(userDTOs);
	            response.setMessage("Consulta exitosa");
	            response.setMetadata("Éxito", "00", "Consulta exitosa");
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            response.setMessage("Error en el servidor");
	            response.setMetadata("Error", "-1", "Error en el servidor");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }


	    @Override
	    public ResponseEntity<UserResponseRest> searchById(Long id) {
	        UserResponseRest response = new UserResponseRest();
	        try {
	            Optional<User> userOpt = userRepository.findById(id);
	            if (!userOpt.isPresent()) {
	                response.setMessage("Usuario no encontrado");
	                response.setMetadata("Error", "404", "Usuario no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	            User user = userOpt.get();
	            UserDTO userDTO = new UserDTO();
	            userDTO.setId(user.getId());
	            userDTO.setEmail(user.getEmail());
	            userDTO.setName(user.getName());
	            userDTO.setLastName(user.getLastName());

	            response.setUser(userDTO);
	            response.setMessage("Usuario encontrado");
	            response.setMetadata("Éxito", "00", "Usuario encontrado");
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            response.setMessage("Error en el servidor");
	            response.setMetadata("Error", "-1", "Error en el servidor");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }


	    @Override
	    public ResponseEntity<UserResponseRest> update(UserDTO userDTO, Long id) {
	        UserResponseRest response = new UserResponseRest();
	        try {
	            Optional<User> userOpt = userRepository.findById(id);
	            if (!userOpt.isPresent()) {
	                response.setMessage("Usuario no encontrado");
	                response.setMetadata("Error", "404", "Usuario no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	            User user = userOpt.get();
	 
	            if (userDTO.getEmail() != null) {
	                user.setEmail(userDTO.getEmail());
	            }
	            if (userDTO.getName() != null) {
	                user.setName(userDTO.getName());
	            }
	            if (userDTO.getLastName() != null) {
	                user.setLastName(userDTO.getLastName());
	            }

	            userRepository.save(user);

	            response.setMessage("Usuario actualizado correctamente");
	            response.setMetadata("Éxito", "00", "Actualización exitosa");

	            UserDTO updatedUserDTO = new UserDTO();
	            updatedUserDTO.setId(user.getId());
	            updatedUserDTO.setEmail(user.getEmail());
	            updatedUserDTO.setName(user.getName());
	            updatedUserDTO.setLastName(user.getLastName());

	            response.setUser(updatedUserDTO);
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            response.setMessage("Error al actualizar el usuario");
	            response.setMetadata("Error", "-1", "Error en el servidor");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }


	    @Override
	    public ResponseEntity<UserResponseRest> deleteById(Long id) {
	        UserResponseRest response = new UserResponseRest();
	        try {
	            Optional<User> userOpt = userRepository.findById(id);
	            if (!userOpt.isPresent()) {
	                response.setMessage("Usuario no encontrado");
	                response.setMetadata("Error", "404", "Usuario no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	            userRepository.deleteById(id);
	            response.setMessage("Usuario eliminado correctamente");
	            response.setMetadata("Éxito", "00", "Eliminación exitosa");
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            response.setMessage("Error al eliminar el usuario");
	            response.setMetadata("Error", "-1", "Error en el servidor");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }


	
	@Override
	public ResponseEntity<UserResponseRest> registerUser(RegisterUserDTO registerUserDTO, String ipAddress) {
	    UserResponseRest response = new UserResponseRest();

	    try {

	        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
	            response.setMessage("El correo ya está registrado");
	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	        }


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
	        e.printStackTrace(); 
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


	@Override
	public ResponseEntity<UserResponseRest> updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
	    UserResponseRest response = new UserResponseRest();
	    try {
	        // validar que la nueva contraseña
	        if (passwordUpdateDTO.getNewPassword() == null || passwordUpdateDTO.getNewPassword().length() < 8) {
	            response.setMessage("La nueva contraseña debe tener al menos 8 caracteres.");
	            response.setMetadata("Error", "400", "Contraseña inválida");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }

	        // contraseñas deban coincidir
	        if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
	            response.setMessage("Las contraseñas no coinciden.");
	            response.setMetadata("Error", "400", "Las contraseñas no coinciden");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }

	        // Buscar el usuario por ID
	        Optional<User> userOpt = userRepository.findById(id);
	        if (!userOpt.isPresent()) {
	            response.setMessage("Usuario no encontrado");
	            response.setMetadata("Error", "404", "Usuario no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }

	        User user = userOpt.get();

	        String encryptedPassword = passwordEncoder.encode(passwordUpdateDTO.getNewPassword());
	        user.setPassword(encryptedPassword);
	        userRepository.save(user);

	        response.setMessage("Contraseña actualizada correctamente");
	        response.setMetadata("Éxito", "00", "Contraseña actualizada");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        response.setMessage("Error al actualizar la contraseña");
	        response.setMetadata("Error", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@Override
	public ResponseEntity<UserResponseRest> searchByEmail(String email) {
		   UserResponseRest response = new UserResponseRest();

	        try {
	            Optional<User> user = userRepository.findByEmail(email);

	            if (user.isPresent()) {
	                response.setUser(user.get());
	                response.setMessage("Usuario encontrado.");
	                return new ResponseEntity<>(response, HttpStatus.OK);
	            } else {
	                response.setMessage("Usuario no encontrado.");
	                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	            }
	        } catch (Exception e) {
	            response.setMessage("Error al buscar el usuario por correo.");
	            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	

	
}
