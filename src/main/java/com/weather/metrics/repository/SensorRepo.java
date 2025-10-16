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

//    @Query("SELECT sd FROM SENSOR_DATA sd WHERE " +
//            "(:sensorIds IS NULL OR sd.sensorId IN :sensorIds) AND " +
//            "sd.timestamp BETWEEN :start AND :end")
//    List<Sensor> findWithOptionalSensorsAndDateRange(
//            @Param("sensorIds") List<String> sensorIds,
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end);

    @Query("SELECT sd FROM Sensor sd WHERE " +
            "(:sensorIds IS NULL OR sd.sensorId IN :sensorIds) AND " +
            "(:metrics IS NULL OR sd.metricName IN :metrics) AND " +
            "sd.timestamp BETWEEN :start AND :end")
    List<Sensor> findMetrics(
            @Param("sensorIds") List<String> sensorIds,
            @Param("metrics") List<String> metrics,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
