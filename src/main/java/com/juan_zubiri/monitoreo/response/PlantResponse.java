package com.juan_zubiri.monitoreo.response;

import java.util.List;

import com.juan_zubiri.monitoreo.dto.PlantDTO;
import lombok.Data;


@Data
public class PlantResponse {
    private List<PlantDTO> plants; //Plant DTO
}
