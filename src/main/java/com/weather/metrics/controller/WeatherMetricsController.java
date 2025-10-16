package com.weather.metrics.controller;



import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsRequest;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.service.WeatherSensorMetricsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

//@RestController
//@RequestMapping(value = "/api/v1/weather-metrics")
//public class WeatherMetricsController {
//
//    @Autowired
//    private WeatherSensorMetricsService weatherService;
//
//    @PostMapping("/metrics")
//    public void addMetric(@RequestBody MetricsRequest metricRequest) {
//        weatherService.saveSensorData(metricRequest);
//    }
//
////    @PostMapping("/metrics/query")
////    public List<Map<String, Object>> queryMetrics(@RequestBody MetricsQuery metricQuery) {
////        return weatherService.queryMetrics(metricQuery);
////    }
//}


@RestController
@RequestMapping("/api/v1/weather-metrics")
public class WeatherMetricsController {

    @Autowired
    private WeatherSensorMetricsService weatherMetricsService;

    @PostMapping("/save")
    public ResponseEntity<Sensor> createSensorData(@Valid @RequestBody MetricsRequest request) {

        Sensor sensorData = new Sensor(
                request.getSensorId(),
                request.getMetricName(),
                request.getMetricValue(),
                request.getTimestamp()
        );
        Sensor savedData = weatherMetricsService.saveSensorData(sensorData);
        return ResponseEntity.ok(savedData);
    }

//    @PostMapping("/query")
//    public ResponseEntity<SensorQueryResponse> querySensorData(@Valid @RequestBody SensorQueryRequest request) {
//        SensorQueryResponse response = sensorDataService.querySensorData(request);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SensorData>> getAllSensorData() {
//        List<SensorData> sensorData = sensorDataService.getAllSensorData();
//        return ResponseEntity.ok(sensorData);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<SensorQueryResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
//        SensorQueryResponse response = new SensorQueryResponse();
//        response.setMessage("Error: " + ex.getMessage());
//        return ResponseEntity.badRequest().body(response);
//    }
}

