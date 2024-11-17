package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Readings;
import lombok.Data;

@Data
public class ReadingsResponse {
	
	private List<Readings> readings;

}
