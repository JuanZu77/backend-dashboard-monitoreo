package com.juan_zubiri.monitoreo.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @Size(max = 100, message = "El correo no puede tener más de 100 caracteres")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants;
	
}
