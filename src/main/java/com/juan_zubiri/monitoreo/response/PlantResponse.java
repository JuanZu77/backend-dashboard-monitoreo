package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Plant;
import lombok.Data;

@Data
public class PlantResponse {
	
	private List<Plant> plant;

}
