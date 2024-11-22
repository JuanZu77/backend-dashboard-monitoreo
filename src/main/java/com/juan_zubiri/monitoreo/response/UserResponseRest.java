package com.juan_zubiri.monitoreo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseRest extends ResponseRest{

	private UserResponse userResponse = new UserResponse();
	
	private String message; // msj de respuesta
	
    //private User user; //incluir el usuario
    private Object user; // ahora puedo manejarr un objeto User u otra cosa según necesidad

    
    // Método para establecer el mensaje
    public void setMessage(String message) {
        this.message = message;
    }
    
    private String token;
    
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
