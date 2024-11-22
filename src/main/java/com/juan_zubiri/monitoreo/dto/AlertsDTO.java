package com.juan_zubiri.monitoreo.dto;

import lombok.Data;

@Data
public class AlertsDTO {
    private Long id;
    private Long readingsId; 
    private String alertType; //  (RED, MEDIUM)
    private String description; 
}
