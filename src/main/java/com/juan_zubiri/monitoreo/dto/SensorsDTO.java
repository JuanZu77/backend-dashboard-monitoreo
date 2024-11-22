package com.juan_zubiri.monitoreo.dto;

import lombok.Data;

@Data
public class SensorsDTO {
    private Long id;
    private Long readingsId; 
    private String sensorName;
    private String reason;
    private String sensorType;
}