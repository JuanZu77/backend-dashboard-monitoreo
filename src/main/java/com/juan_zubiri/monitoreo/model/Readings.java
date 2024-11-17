package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private Plant plant;

    @Column(name = "readings_number", nullable = false)
    private Integer readings_number;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "readings", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerts> alerts;

    @OneToMany(mappedBy = "readings", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sensors> sensors;
}