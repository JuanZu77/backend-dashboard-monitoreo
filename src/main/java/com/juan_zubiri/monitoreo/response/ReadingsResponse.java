package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.ReadingsDTO;
import lombok.Data;

@Data
public class ReadingsResponse {
	
	 private List<ReadingsDTO> readings;

	    public void setReadings(List<ReadingsDTO> list) {
	        this.readings = list;
	    }

}
