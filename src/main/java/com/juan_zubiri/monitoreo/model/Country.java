package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "flag_url", nullable = false)
    private String flagUrl;


}

// mappedBy: la relacion viene de plant
//Cascade: si elimino un Pais se eliminan las Plantas asociadas al pais
//orphanRemoval: me aseguro que no queden Plantas sin paises asociados