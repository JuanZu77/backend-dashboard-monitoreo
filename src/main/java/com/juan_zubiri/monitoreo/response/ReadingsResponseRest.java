package com.juan_zubiri.monitoreo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadingsResponseRest extends ResponseRest {

	private ReadingsResponse readingsResponse = new ReadingsResponse();
}
