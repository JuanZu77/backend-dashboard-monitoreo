package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.AlertsDTO;
import lombok.Data;

@Data
public class AlertsResponse {
	
    private List<AlertsDTO> alerts;

}
