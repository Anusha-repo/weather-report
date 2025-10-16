//package com.weather.metrics.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "METRICS")
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class Metrics {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "sensor_id", nullable = false)
//    private Sensor sensorId;
//
//    private String name;
//    private Double value;
//    private LocalDateTime timestamp;
//}
