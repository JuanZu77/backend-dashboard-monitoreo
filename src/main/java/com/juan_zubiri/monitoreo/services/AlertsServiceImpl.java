package com.juan_zubiri.monitoreo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.AlertRepository;
import com.juan_zubiri.monitoreo.model.Alerts;
import com.juan_zubiri.monitoreo.model.Alerts.AlertType;
import com.juan_zubiri.monitoreo.response.AlertsResponseRest;

@Service
public class AlertsServiceImpl implements IAlertsService{
	
	@Autowired
	private AlertRepository alertRepository;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<AlertsResponseRest> search() {
		
		AlertsResponseRest response = new AlertsResponseRest();
		
		 try {
		    	List<Alerts> alerts = alertRepository.findAll(); 
		        response.getAlertsResponse().setAlerts(alerts);
		        response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<AlertsResponseRest> searchById(Long id) {
		
		AlertsResponseRest response = new AlertsResponseRest();
		
		try {
	        Optional<Alerts> alert = alertRepository.findById(id);
	        if (alert.isPresent()) {
	            response.getAlertsResponse().setAlerts(List.of(alert.get()));
	            response.setMetadata("Respuesta exitosa", "00", "Consulta exitosa");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al consultar", "-1", "Alerta no encontrado");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al consultar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<AlertsResponseRest> save(Alerts alerts) {
	
		AlertsResponseRest response = new AlertsResponseRest();
		
		try {
        // guardo la alerta
        Alerts savedAlert = alertRepository.save(alerts);

        // respuesta
        response.setMetadata("Éxito", "200", "Alertas guardada correctamente");
        response.getAlertsResponse().setAlerts(Collections.singletonList(savedAlert));
        return ResponseEntity.ok(response);
		
       } catch (Exception e) {
        response.setMetadata("Error", "500", "Error al guardar las Alertas: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
	}

	@Override
	@Transactional
	public ResponseEntity<AlertsResponseRest> update(Alerts alerts, Long id) {
		
		AlertsResponseRest response = new AlertsResponseRest();
		
		try {
	        Optional<Alerts> alertOptional = alertRepository.findById(id);
	        if (alertOptional.isPresent()) {
	            Alerts alertToUpdate = alertOptional.get();
	            alertToUpdate.setAlertType(alerts.getAlertType()); 
	           
	            alertRepository.save(alertToUpdate);
	            response.getAlertsResponse().setAlerts(List.of(alertToUpdate));
	            response.setMetadata("Respuesta exitosa", "00", "Alerta actualizada correctamente");
	            return ResponseEntity.ok(response);
	        } else {
	            response.setMetadata("Error al actualizar", "-1", "Alerta no encontrada");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    } catch (Exception e) {
	        response.setMetadata("Error al actualizar", "-1", "Error en el servidor");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<AlertsResponseRest> deleteById(Long id) {
		
		AlertsResponseRest response = new AlertsResponseRest();
		
		 try {
		        Optional<Alerts> alertOptional = alertRepository.findById(id);
		        if (alertOptional.isPresent()) {
		            alertRepository.deleteById(id);
		            response.setMetadata("Respuesta exitosa", "00", "Alerta eliminada correctamente");
		            return ResponseEntity.ok(response);
		        } else {
		            response.setMetadata("Error al eliminar", "-1", "Alerta no encontrada");
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		        }
		    } catch (Exception e) {
		        response.setMetadata("Error al eliminar", "-1", "Error en el servidor");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }
	}

	@Override
	public int countAlertsByType(AlertType alertType) {
	    return alertRepository.countByAlertType(alertType); 
	}

	@Override
	public int countTotalAlerts() {
	    return (int) alertRepository.count(); 
	}


}
