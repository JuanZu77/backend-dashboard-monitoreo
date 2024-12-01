package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "readings")
public class Readings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_readings")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plant", nullable = false)
    @JsonBackReference //lecturas pertenen a una planta
    private Plant plant;

    @Column(name = "readings_number", nullable = false)
    private Integer readings_number;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "readings", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //lecturas tien muchas alertas
    private List<Alerts> alerts;

    @OneToMany(mappedBy = "readings", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //lecturas tienen muchas alertas
    private List<Sensors> sensors;
}