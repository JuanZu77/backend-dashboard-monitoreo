package com.juan_zubiri.monitoreo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "sensors")
public class Sensors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sensors")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_lectures", nullable = false)
    private Lectures lecture;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensors_type", nullable = false)
    private SensorType sensorType;

    @Column(name = "sensors_name", nullable = false, length = 100)
    private String sensorName;

    @Column(name = "reason", length = 255)
    private String reason;

    public enum SensorType {
        ACTIVE, DISABLED
    }
}