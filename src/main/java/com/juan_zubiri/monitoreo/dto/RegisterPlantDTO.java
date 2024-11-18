package com.juan_zubiri.monitoreo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterPlantDTO {

    @NotBlank(message = "El nombre de la planta es obligatorio")
    @Size(max = 100, message = "El nombre de la planta no puede tener más de 100 caracteres")
    private String name;

    @NotNull(message = "El país es obligatorio")
    private Long countryId;  // Usamos solo el ID del país, ya que no queremos enviar toda la entidad

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;     // Usamos solo el ID del usuario
}
