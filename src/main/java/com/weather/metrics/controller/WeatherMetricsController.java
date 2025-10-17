package com.weather.metrics.controller;

import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsRequest;
import com.weather.metrics.dto.MetricsResponse;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.service.WeatherSensorMetricsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/weather-metrics")
public class WeatherMetricsController {

    @Autowired
    private WeatherSensorMetricsService weatherMetricsService;

    @PostMapping("/save")
    public ResponseEntity<Sensor> createSensorData(@Valid @RequestBody MetricsRequest request) {
        log.info("Received request to save sensor data"+ request);

        Sensor sensorData = new Sensor(
                request.getSensorId(),
                request.getMetricName(),
                request.getMetricValue(),
                request.getTimestamp()
        );
        Sensor savedData = weatherMetricsService.saveSensorData(sensorData);
        log.info("Sensor data saved successfully for id" +" "+ request.getSensorId());
        return ResponseEntity.ok(savedData);
    }

    @PostMapping("/query")
    public ResponseEntity<MetricsResponse> querySensorData(@Valid @RequestBody MetricsQuery request) {

        log.info("Received request to query sensor data"+ request);
        MetricsResponse response = weatherMetricsService.querySensorData(request);
        log.info("Sensor data queried successfully for id's"+ request.getSensorIds() +" and metrics "+ request.getMetricNames());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allSensorData")
    public ResponseEntity<List<Sensor>> getAllSensorData() {
        List<Sensor> sensorData = weatherMetricsService.getAllSensorData();
        log.info("All sensor data retrieved successfully");
        return ResponseEntity.ok(sensorData);
    }
}

