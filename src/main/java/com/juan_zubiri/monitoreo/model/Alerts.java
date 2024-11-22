package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "alerts")
public class Alerts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerts")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_readings", nullable = false)
    private Readings readings;

    @Enumerated(EnumType.STRING)
    @Column(name = "alerts_type", nullable = false)
    private AlertType alertType;

    @Column(name = "description", length = 255)
    private String description;

    public enum AlertType {
        RED, MEDIUM
    }
}