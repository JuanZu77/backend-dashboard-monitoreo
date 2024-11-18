package com.juan_zubiri.monitoreo.services;

import org.springframework.http.ResponseEntity;
import com.juan_zubiri.monitoreo.model.Alerts;
import com.juan_zubiri.monitoreo.model.Alerts.AlertType;
import com.juan_zubiri.monitoreo.response.AlertsResponseRest;

public interface IAlertsService {

	public ResponseEntity<AlertsResponseRest> search();
	public ResponseEntity<AlertsResponseRest> searchById(Long id);
	
	public ResponseEntity<AlertsResponseRest> save(Alerts alerts);
	
	public ResponseEntity<AlertsResponseRest> update(Alerts alerts, Long id);
	
	public ResponseEntity<AlertsResponseRest> deleteById(Long id);
	
	int countAlertsByType(AlertType alertType); 
	int countTotalAlerts(); 

}
