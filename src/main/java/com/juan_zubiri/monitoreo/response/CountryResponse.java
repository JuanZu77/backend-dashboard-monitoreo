package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.CountryDTO;
import lombok.Data;

@Data
public class CountryResponse {
	
	 private List<CountryDTO> country;

}
