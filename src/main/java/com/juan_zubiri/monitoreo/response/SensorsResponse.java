package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Sensors;
import lombok.Data;

@Data
public class SensorsResponse {
	
	private List<Sensors> sensors;

}
