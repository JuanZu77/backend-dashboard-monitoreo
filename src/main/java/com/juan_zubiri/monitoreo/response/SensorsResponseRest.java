package com.juan_zubiri.monitoreo.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorsResponseRest extends ResponseRest{

	private SensorsResponse sensorsResponse = new SensorsResponse();

	 private String message; // Para mensajes adicionales

	    public void setMessage(String message) {
	        this.message = message;
	    }

}
