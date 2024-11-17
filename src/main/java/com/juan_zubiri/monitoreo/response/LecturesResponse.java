package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Lectures;
import lombok.Data;

@Data
public class LecturesResponse {
	
	private List<Lectures> lectures;

}
