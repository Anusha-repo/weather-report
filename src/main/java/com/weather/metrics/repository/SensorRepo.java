package com.weather.metrics.repository;

import com.weather.metrics.dto.QueryResults;
import com.weather.metrics.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor,Long> {

//    @Query("SELECT sd FROM Sensor sd WHERE " +
//            "(:sensorIds IS NULL OR sd.sensorId IN :sensorIds) AND " +
//            "(:metrics IS NULL OR sd.metricName IN :metrics) AND " +
//            "sd.timestamp BETWEEN :start AND :end")
//    List<Sensor> findMetrics(
//            @Param("sensorIds") List<String> sensorIds,
//            @Param("metrics") List<String> metrics,
//            @Param("start") LocalDate start,
//            @Param("end") LocalDate end);


    @Query(value = """
        SELECT sensor_id as sensorId, 
               metric_name as metricName,
               CASE :statistic
                   WHEN 'MIN' THEN MIN(metric_value)
                   WHEN 'MAX' THEN MAX(metric_value) 
                   WHEN 'SUM' THEN SUM(metric_value)
                   WHEN 'AVG' THEN AVG(metric_value)
               END as result
        FROM Sensor
        WHERE (:sensorIds IS NULL OR sensor_id IN :sensorIds)
          AND (:metrics IS NULL OR metric_name IN :metrics)
          AND timestamp BETWEEN :start AND :end
        GROUP BY sensor_id, metric_name
        """, nativeQuery = true)
    List<QueryResults> findAggMetrics(
            @Param("sensorIds") List<String> sensorIds,
            @Param("metrics") List<String> metrics,
            @Param("start") LocalDate startDate,
            @Param("end") LocalDate endDate,
            @Param("statistic") String statistic);
}

