package com.juan_zubiri.monitoreo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlantResponseRest extends ResponseRest{
	
	private PlantResponse plantResponse = new PlantResponse();
	
	 private String message; // Para mensajes adicionales

	    public void setMessage(String message) {
	        this.message = message;
	    }

}
