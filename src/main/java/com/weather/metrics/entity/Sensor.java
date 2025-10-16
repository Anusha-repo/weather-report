package com.weather.metrics.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SENSOR_DATA")
@Data
@Builder
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name="sensor_id")
    private String sensorId;

    @NonNull
    @Column(name="metric_name")
    private String metricName;

    @NonNull
    @Column(name="metric_value")
    private Double metricValue;

    @NonNull
    @Column(name="timestamp")
    private LocalDateTime timestamp;

    // Constructors
    public Sensor() {}

    public Sensor(String sensorId, String metricName, Double metricValue, LocalDateTime timestamp) {
        this.sensorId = sensorId;
        this.metricName = metricName;
        this.metricValue = metricValue;
        this.timestamp = timestamp;
    }
    }

