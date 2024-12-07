package com.juan_zubiri.monitoreo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastName;
}