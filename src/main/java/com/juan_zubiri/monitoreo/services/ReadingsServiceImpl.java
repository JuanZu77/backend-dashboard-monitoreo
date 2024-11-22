package com.juan_zubiri.monitoreo.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.juan_zubiri.monitoreo.dao.AlertRepository;
import com.juan_zubiri.monitoreo.dao.PlantRepository;
import com.juan_zubiri.monitoreo.dao.ReadingsRepository;
import com.juan_zubiri.monitoreo.dao.SensorsRepository;
import com.juan_zubiri.monitoreo.dto.ReadingsDTO;
import com.juan_zubiri.monitoreo.model.Alerts;
import com.juan_zubiri.monitoreo.model.Plant;
import com.juan_zubiri.monitoreo.model.Readings;
import com.juan_zubiri.monitoreo.model.Sensors;
import com.juan_zubiri.monitoreo.response.ReadingsResponseRest;

@Service
public class ReadingsServiceImpl implements IReadingsService {

    @Autowired
    private ReadingsRepository readingsRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private AlertRepository alertsRepository;

    @Autowired
    private SensorsRepository sensorsRepository;

//    @Override
//    public ResponseEntity<ReadingsResponseRest> save(ReadingsDTO readingsDTO) {
//        ReadingsResponseRest response = new ReadingsResponseRest();
//
//        try {
//
//            Optional<Plant> plantOpt = plantRepository.findById(readingsDTO.getPlantId());
//            if (!plantOpt.isPresent()) {
//                response.setMetadata("Error", "404", "Planta no encontrada");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//
//         
//            Readings readings = new Readings();
//            Plant plant = plantOpt.get();
//            readings.setPlant(plant);
//            readings.setReadings_number(readingsDTO.getReadingsNumber());
//
//            // necesito que si timestamp no está presente en el DTO, asignarle la fecha y hora actuales del serviodor
//            if (readingsDTO.getTimestamp() == null) {
//                readings.setTimestamp(LocalDateTime.now());  
//            } else {
//                readings.setTimestamp(readingsDTO.getTimestamp());  
//            }
//
//            // guardo los sensores si existen
//            if (readingsDTO.getSensorIds() != null && !readingsDTO.getSensorIds().isEmpty()) {
//                List<Sensors> sensors = sensorsRepository.findAllById(readingsDTO.getSensorIds());
//                readings.setSensors(sensors);
//            }
//
//            // aqui guardo las alertas 
//            if (readingsDTO.getAlertIds() != null && !readingsDTO.getAlertIds().isEmpty()) {
//                List<Alerts> alerts = alertsRepository.findAllById(readingsDTO.getAlertIds());
//                readings.setAlerts(alerts);
//            }
//
//            
//            Readings savedReading = readingsRepository.save(readings);
//
//            //  DTO para la planta 
//            PlantDTO plantDTO = new PlantDTO();
//            plantDTO.setId(plant.getId());
//
//            response.setMetadata("Éxito", "200", "Reading guardado correctamente");
//
//            // creo DTO para Readings 
//            ReadingsDTO responseReading = new ReadingsDTO();
//            responseReading.setId(savedReading.getId());
//            responseReading.setPlantId(savedReading.getPlant().getId());
//            responseReading.setReadingsNumber(savedReading.getReadings_number());
//            responseReading.setTimestamp(savedReading.getTimestamp());
//
//            response.getReadingsResponse().setReadings(Collections.singletonList(responseReading));
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            response.setMetadata("Error", "500", "Error al guardar el Reading: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
    
    @Override
    public ResponseEntity<ReadingsResponseRest> save(ReadingsDTO readingsDTO) {
        ReadingsResponseRest response = new ReadingsResponseRest();

        try {
            // Verifico que la planta exista
            Optional<Plant> plantOpt = plantRepository.findById(readingsDTO.getPlantId());
            if (!plantOpt.isPresent()) {
                response.setMetadata("Error", "404", "Planta no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Readings readings = new Readings();
            Plant plant = plantOpt.get();
            readings.setPlant(plant);

            // cargo el número de lectura de forma incremental
            Integer maxReadingNumber = readingsRepository.findMaxReadingsNumberByPlant(plant.getId());
            readings.setReadings_number((maxReadingNumber != null ? maxReadingNumber : 0) + 1);

            // timestamp si no está presente
            if (readingsDTO.getTimestamp() == null) {
                readings.setTimestamp(LocalDateTime.now());
            } else {
                readings.setTimestamp(readingsDTO.getTimestamp());
            }

            // sensores si existen
            if (readingsDTO.getSensorIds() != null && !readingsDTO.getSensorIds().isEmpty()) {
                List<Sensors> sensors = sensorsRepository.findAllById(readingsDTO.getSensorIds());
                readings.setSensors(sensors);
            }

            // alertas si existen
            if (readingsDTO.getAlertIds() != null && !readingsDTO.getAlertIds().isEmpty()) {
                List<Alerts> alerts = alertsRepository.findAllById(readingsDTO.getAlertIds());
                readings.setAlerts(alerts);
            }

            Readings savedReading = readingsRepository.save(readings);

            // el DTO de respuesta
            ReadingsDTO responseReading = new ReadingsDTO();
            responseReading.setId(savedReading.getId());
            responseReading.setPlantId(savedReading.getPlant().getId());
            responseReading.setReadingsNumber(savedReading.getReadings_number());
            responseReading.setTimestamp(savedReading.getTimestamp());

            response.getReadingsResponse().setReadings(Collections.singletonList(responseReading));
            response.setMetadata("Éxito", "200", "Reading guardado correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMetadata("Error", "500", "Error al guardar el Reading: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




    @Override
    public ResponseEntity<ReadingsResponseRest> deleteById(Long id) {
        ReadingsResponseRest response = new ReadingsResponseRest();

        try {

            Optional<Readings> readingsOpt = readingsRepository.findById(id);
            if (!readingsOpt.isPresent()) {
                response.setMetadata("Error", "404", "Reading no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }


            readingsRepository.deleteById(id);

            response.setMetadata("Éxito", "200", "Reading eliminado correctamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMetadata("Error", "500", "Error al eliminar el Reading: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Override
    public ResponseEntity<ReadingsResponseRest> search() {
        ReadingsResponseRest response = new ReadingsResponseRest();

        try {

            List<Readings> readingsList = readingsRepository.findAll();

            //  la lista de entititys a DTOs
            List<ReadingsDTO> readingsDTOList = readingsList.stream()
                    .map(reading -> {
                        ReadingsDTO dto = new ReadingsDTO();
                        dto.setId(reading.getId());
                        dto.setPlantId(reading.getPlant().getId());
                        dto.setReadingsNumber(reading.getReadings_number());
                        dto.setTimestamp(reading.getTimestamp());
                        dto.setAlertIds(reading.getAlerts() != null ? reading.getAlerts().stream().map(Alerts::getId).collect(Collectors.toList()) : null);
                        dto.setSensorIds(reading.getSensors() != null ? reading.getSensors().stream().map(Sensors::getId).collect(Collectors.toList()) : null);
                        return dto;
                    })
                    .collect(Collectors.toList());

            response.setMetadata("Éxito", "200", "Lecturas encontradas correctamente");
            response.getReadingsResponse().setReadings(readingsDTOList);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMetadata("Error", "500", "Error al buscar los Readings: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Override
    public ResponseEntity<ReadingsResponseRest> searchById(Long id) {
        ReadingsResponseRest response = new ReadingsResponseRest();

        try {

            Optional<Readings> readingsOpt = readingsRepository.findById(id);
            if (!readingsOpt.isPresent()) {
                response.setMetadata("Error", "404", "Reading no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            //  el reading encontrado a DTO
            Readings reading = readingsOpt.get();
            ReadingsDTO dto = new ReadingsDTO();
            dto.setId(reading.getId());
            dto.setPlantId(reading.getPlant().getId());
            dto.setReadingsNumber(reading.getReadings_number());
            dto.setTimestamp(reading.getTimestamp());
            dto.setAlertIds(reading.getAlerts() != null ? reading.getAlerts().stream().map(Alerts::getId).collect(Collectors.toList()) : null);
            dto.setSensorIds(reading.getSensors() != null ? reading.getSensors().stream().map(Sensors::getId).collect(Collectors.toList()) : null);

            response.setMetadata("Éxito", "200", "Lectura encontrada correctamente");
            response.getReadingsResponse().setReadings(Collections.singletonList(dto));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMetadata("Error", "500", "Error al buscar el Reading: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @Override
    public ResponseEntity<ReadingsResponseRest> update(ReadingsDTO readingsDTO, Long id) {
        ReadingsResponseRest response = new ReadingsResponseRest();

        try {

            Optional<Readings> readingsOpt = readingsRepository.findById(id);
            if (!readingsOpt.isPresent()) {
                response.setMetadata("Error", "404", "Lectura no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Readings readings = readingsOpt.get();


            if (readingsDTO.getPlantId() != null) {
                Optional<Plant> plantOpt = plantRepository.findById(readingsDTO.getPlantId());
                if (!plantOpt.isPresent()) {
                    response.setMetadata("Error", "404", "Planta no encontrada");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
                readings.setPlant(plantOpt.get());
            }

         
            if (readingsDTO.getReadingsNumber() != null) {
                readings.setReadings_number(readingsDTO.getReadingsNumber());
            }

     
            if (readingsDTO.getTimestamp() != null) {
                readings.setTimestamp(readingsDTO.getTimestamp());
            } else {
                readings.setTimestamp(LocalDateTime.now());
            }


            if (readingsDTO.getSensorIds() != null && !readingsDTO.getSensorIds().isEmpty()) {
                List<Sensors> sensors = sensorsRepository.findAllById(readingsDTO.getSensorIds());
                readings.setSensors(sensors);
            }


            if (readingsDTO.getAlertIds() != null && !readingsDTO.getAlertIds().isEmpty()) {
                List<Alerts> alerts = alertsRepository.findAllById(readingsDTO.getAlertIds());
                readings.setAlerts(alerts);
            }

 
            Readings updatedReading = readingsRepository.save(readings);

            ReadingsDTO responseReading = new ReadingsDTO();
            responseReading.setId(updatedReading.getId());
            responseReading.setPlantId(updatedReading.getPlant().getId());
            responseReading.setReadingsNumber(updatedReading.getReadings_number());
            responseReading.setTimestamp(updatedReading.getTimestamp());

            response.getReadingsResponse().setReadings(Collections.singletonList(responseReading));
            response.setMetadata("Éxito", "200", "Lectura actualizada correctamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.setMetadata("Error", "500", "Error al actualizar la lectura: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



	@Override
	public int countTotalReadings() {
		 return (int) readingsRepository.count(); 
	}


}
