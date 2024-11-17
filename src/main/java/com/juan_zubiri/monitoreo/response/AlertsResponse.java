package com.juan_zubiri.monitoreo.response;

import java.util.List;
import com.juan_zubiri.monitoreo.model.Alerts;
import lombok.Data;

@Data
public class AlertsResponse {
	
	private List<Alerts> alerts;

}
