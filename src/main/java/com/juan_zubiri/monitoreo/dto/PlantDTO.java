package com.juan_zubiri.monitoreo.dto;

import lombok.Data;

@Data
public class PlantDTO {
    private Long id;
    private String name;
    private Long userId;     
    private String userName;  
    private Long countryId;   
    private String countryName; 
}

