package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Country;
import lombok.Data;

@Data
public class CountryResponse {
	
	private List<Country> country;

}
