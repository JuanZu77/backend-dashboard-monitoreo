package com.juan_zubiri.monitoreo.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juan_zubiri.monitoreo.dao.AlertRepository;
import com.juan_zubiri.monitoreo.dao.ReadingsRepository;
import com.juan_zubiri.monitoreo.dto.AlertsDTO;
import com.juan_zubiri.monitoreo.model.Alerts;
import com.juan_zubiri.monitoreo.model.Alerts.AlertType;
import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.response.AlertsResponseRest;

@Service
public class AlertsServiceImpl implements IAlertsService{
	
	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private ReadingsRepository readingsRepository;


	 @Override
	    public ResponseEntity<AlertsResponseRest> save(AlertsDTO alertDTO) {
	        AlertsResponseRest response = new AlertsResponseRest();

	        try {
	           
	            Optional<Readings> readingsOpt = readingsRepository.findById(alertDTO.getReadingsId());
	            if (!readingsOpt.isPresent()) {
	                response.setMetadata("Error", "404", "Reading no encontrado");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }

	    
	            Alerts alert = new Alerts();
	            Readings readings = readingsOpt.get();
	            alert.setReadings(readings);


	            alert.setAlertType(AlertType.valueOf(alertDTO.getAlertType()));
	            alert.setDescription(alertDTO.getDescription());


	            Alerts savedAlert = alertRepository.save(alert);

	
	            AlertsDTO responseAlert = new AlertsDTO();
	            responseAlert.setId(savedAlert.getId());
	            responseAlert.setReadingsId(savedAlert.getReadings().getId());
	            responseAlert.setAlertType(savedAlert.getAlertType().name());
	            responseAlert.setDescription(savedAlert.getDescription());

	            response.getAlertsResponse().setAlerts(Collections.singletonList(responseAlert));
	            response.setMetadata("Éxito", "200", "Alerta guardada correctamente");

	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.setMetadata("Error", "500", "Error al guardar la alerta: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }
	 
	 
	 
	 @Override
	 @Transactional(readOnly = true)
	 public ResponseEntity<AlertsResponseRest> search() {
	     AlertsResponseRest response = new AlertsResponseRest();

	     try {
	         List<Alerts> alertsList = alertRepository.findAll(); 
	         if (alertsList.isEmpty()) {
	             response.setMetadata("Advertencia", "404", "No se encontraron alertas");
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	         }


	         List<AlertsDTO> alertsDTOList = new ArrayList<>();
	         for (Alerts alert : alertsList) {
	             AlertsDTO alertDTO = new AlertsDTO();
	             alertDTO.setId(alert.getId());
	             alertDTO.setReadingsId(alert.getReadings().getId());
	             alertDTO.setAlertType(alert.getAlertType().name());
	             alertDTO.setDescription(alert.getDescription());
	             alertsDTOList.add(alertDTO);
	         }


	         response.getAlertsResponse().setAlerts(alertsDTOList);
	         response.setMetadata("Éxito", "200", "Alertas recuperadas correctamente");

	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         response.setMetadata("Error", "500", "Error al recuperar las alertas: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }



	 @Override
	 @Transactional(readOnly = true)
	 public ResponseEntity<AlertsResponseRest> searchById(Long id) {
	     AlertsResponseRest response = new AlertsResponseRest();

	     try {
	         Optional<Alerts> alertOpt = alertRepository.findById(id); 
	         if (!alertOpt.isPresent()) {
	             response.setMetadata("Error", "404", "Alerta no encontrada");
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	         }

	
	         Alerts alert = alertOpt.get();
	         AlertsDTO alertDTO = new AlertsDTO();
	         alertDTO.setId(alert.getId());
	         alertDTO.setReadingsId(alert.getReadings().getId());
	         alertDTO.setAlertType(alert.getAlertType().name());
	         alertDTO.setDescription(alert.getDescription());

	         response.getAlertsResponse().setAlerts(Collections.singletonList(alertDTO));
	         response.setMetadata("Éxito", "200", "Alerta recuperada correctamente");

	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         response.setMetadata("Error", "500", "Error al recuperar la alerta: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }



	 @Override
	 public ResponseEntity<AlertsResponseRest> deleteById(Long id) {
	     AlertsResponseRest response = new AlertsResponseRest();

	     try {
	         Optional<Alerts> alertOpt = alertRepository.findById(id);
	         if (!alertOpt.isPresent()) {
	             response.setMetadata("Error", "404", "Alerta no encontrada");
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	         }

	         alertRepository.deleteById(id); 

	         response.setMetadata("Éxito", "200", "Alerta eliminada correctamente");
	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         response.setMetadata("Error", "500", "Error al eliminar la alerta: " + e.getMessage());
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }
	 }

	
	 @Override
	 public ResponseEntity<AlertsResponseRest> update(AlertsDTO alertDTO, Long id) {
	     AlertsResponseRest response = new AlertsResponseRest();

	     try {
	         Optional<Alerts> alertOpt = alertRepository.findById(id); 
	         if (!alertOpt.isPresent()) {
	             response.setMetadata("Error", "404", "Alerta no encontrada");
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	         }

	         Alerts alert = alertOpt.get();
	
	         if (alertDTO.getAlertType() != null) {
	             alert.setAlertType(AlertType.valueOf(alertDTO.getAlertType()));
	         }
	         if (alertDTO.getDescription() != null) {
	             alert.setDescription(alertDTO.getDescription());
	         }

	         // fguardo la alerta actualizada
	         Alerts updatedAlert = alertRepository.save(alert);

	         // paso la alerta actualizada a DTO
	         AlertsDTO updatedAlertDTO = new AlertsDTO();
	         updatedAlertDTO.setId(updatedAlert.getId());
	         updatedAlertDTO.setReadingsId(updatedAlert.getReadings().getId());
	         updatedAlertDTO.setAlertType(updatedAlert.getAlertType().name());
	         updatedAlertDTO.setDescription(updatedAlert.getDescription());

	         response.getAlertsResponse().setAlerts(Collections.singletonList(updatedAlertDTO));
	         response.setMetadata("Éxito", "200", "Alerta actualizada correctamente");

	         return ResponseEntity.ok(response);

	     } catch (Exception e) {
	         response.setMetadata("Error", "500", "Error al actualizar la alerta: " + e.getMessage());
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
