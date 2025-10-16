package com.weather.metrics.repository;

import com.weather.metrics.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor,Long> {

//    List<Sensor> findBySensorIdAndMetricTypeAndTimestampBetween(
//            String sensorId, String metricType, LocalDateTime start, LocalDateTime end);
//
//    List<Sensor> findBySensorIdInAndMetricTypeAndTimestampBetween(
//            List<String> sensorIds, String metricType, LocalDateTime start, LocalDateTime end);
//
//    List<Sensor> findBySensorIdAndTimestampBetween(
//            String sensorId, LocalDateTime start, LocalDateTime end);
//
//    List<Sensor> findBySensorIdInAndTimestampBetween(
//            List<String> sensorIds, LocalDateTime start, LocalDateTime end);
//
//    @Query("SELECT sd FROM SensorData sd WHERE " +
//            "(:sensorIds IS NULL OR sd.sensorId IN :sensorIds) AND " +
//            "sd.timestamp BETWEEN :start AND :end")
//    List<Sensor> findWithOptionalSensorsAndDateRange(
//            @Param("sensorIds") List<String> sensorIds,
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end);

//    @Query("SELECT sd FROM SensorData sd WHERE " +
//            "(:sensorIds IS NULL OR sd.sensorId IN :sensorIds) AND " +
//            "(:metrics IS NULL OR sd.metricType IN :metrics) AND " +
//            "sd.timestamp BETWEEN :start AND :end")
//    List<Sensor> findWithOptionalSensorsAndMetricsAndDateRange(
//            @Param("sensorIds") List<String> sensorIds,
//            @Param("metrics") List<String> metrics,
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end);
}
