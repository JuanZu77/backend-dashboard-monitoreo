package com.juan_zubiri.monitoreo.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReadingsDTO {
    private Long id;
    private Long plantId;  
    private Integer readingsNumber;  
    private LocalDateTime timestamp;  
    private List<Long> alertIds; 
    private List<Long> sensorIds; 
}
