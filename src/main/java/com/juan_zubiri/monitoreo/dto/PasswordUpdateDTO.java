package com.juan_zubiri.monitoreo.dto;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String newPassword;
    private String confirmPassword;
}

