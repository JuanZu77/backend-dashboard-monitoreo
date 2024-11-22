package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.SensorsDTO;


import lombok.Data;

@Data
public class SensorsResponse {
	
	private List<SensorsDTO> sensors;

}
