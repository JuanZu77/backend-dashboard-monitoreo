package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "plant")
public class Plant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plant")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonBackReference //un usario tiene muchos plantas
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_country", nullable = true)
    @JsonBackReference //un pais tiene muchas plantas
    private Country country;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //una planta tiene muchas lecturas
    private List<Readings> readings;

}