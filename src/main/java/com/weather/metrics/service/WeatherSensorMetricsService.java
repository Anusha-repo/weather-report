package com.weather.metrics.service;


import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsRequest;
import com.weather.metrics.dto.MetricsResponse;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.repository.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherSensorMetricsService {

    @Autowired
    private SensorRepo sensorDataRepository;

    public Sensor saveSensorData(Sensor sensorData) {

            if (sensorData.getTimestamp() == null) {
                sensorData.setTimestamp(LocalDateTime.now());
            }
            return sensorDataRepository.save(sensorData);
        }


//    public MetricsResponse querySensorData(MetricsQuery request) {
//        // Validate date range
////        validateDateRange(request);
//
//        // Set default date range if not provided (last 24 hours)
//        LocalDateTime endDate = request.getEndDate() != null ? request.getEndDate() : LocalDateTime.now();
//        LocalDateTime startDate = request.getStartDate() != null ? request.getStartDate() : endDate.minusDays(1);
//
//        // Fetch data based on query parameters
//        List<Sensor> sensorDataList = fetchSensorData(request, startDate, endDate);
//
//        // Calculate statistics
//        Map<String, Map<String, Double>> results = calculateStatistics(sensorDataList, request);
//
//        MetricsResponse response = new MetricsResponse();
//        response.setResults(results);
//        response.setMessage("Query executed successfully");
//
//        return response;
//    }
//
////    private void validateDateRange(MetricsRequest request) {
////        if (request.getStartDate() != null && request.getEndDate() != null) {
////            if (request.getStartDate().isAfter(request.getEndDate())) {
////                throw new IllegalArgumentException("Start date cannot be after end date");
////            }
////
////            // Check if date range is between 1 day and 1 month
////            long daysBetween = java.time.Duration.between(request.getStartDate(), request.getEndDate()).toDays();
////            if (daysBetween < 1 || daysBetween > 31) {
////                throw new IllegalArgumentException("Date range must be between 1 day and 1 month");
////            }
////        }
////    }
//
//    private List<Sensor> fetchSensorData(MetricsQuery request, LocalDateTime startDate, LocalDateTime endDate) {
//        List<String> sensorIds = request.getSensorIds();
//        List<String> metrics = request.getMetricNames();
//
//        if (sensorIds != null && !sensorIds.isEmpty() && metrics != null && !metrics.isEmpty()) {
//            return sensorDataRepository.findWithOptionalSensorsAndMetricsAndDateRange(sensorIds, metrics, startDate, endDate);
//        } else if (sensorIds != null && !sensorIds.isEmpty()) {
//            return sensorDataRepository.findWithOptionalSensorsAndDateRange(sensorIds, startDate, endDate);
//        } else {
//            return sensorDataRepository.findWithOptionalSensorsAndDateRange(null, startDate, endDate);
//        }
//    }
//
//    private Map<String, Map<String, Double>> calculateStatistics(List<Sensor> sensorDataList, MetricsQuery request) {
//        // Group by sensor and metric
//        Map<String, Map<String, List<Double>>> groupedData = sensorDataList.stream()
//                .collect(Collectors.groupingBy(
//                        Sensor::getSensorId,
//                        Collectors.groupingBy(
//                                Sensor::getMetricName,
//                                Collectors.mapping(Sensor::getMetricValue, Collectors.toList())
//                        )
//                ));
//
//        Map<String, Map<String, Double>> results = new HashMap<>();
//
//        for (Map.Entry<String, Map<String, List<Double>>> sensorEntry : groupedData.entrySet()) {
//            String sensorId = sensorEntry.getKey();
//            Map<String, List<Double>> metricData = sensorEntry.getValue();
//
//            Map<String, Double> sensorResults = new HashMap<>();
//
//            for (Map.Entry<String, List<Double>> metricEntry : metricData.entrySet()) {
//                String metric = metricEntry.getKey();
//                List<Double> values = metricEntry.getValue();
//
//                if (values.isEmpty()) continue;
//
//                double result = calculateStatistic(values, request.getStatistic());
//                sensorResults.put(metric, result);
//            }
//
//            if (!sensorResults.isEmpty()) {
//                results.put(sensorId, sensorResults);
//            }
//        }
//
//        return results;
//    }
//
//    private double calculateStatistic(List<Double> values, String statistic) {
//        return switch (statistic) {
//            case "min" -> values.stream().min(Double::compare).orElse(0.0);
//            case "max" -> values.stream().max(Double::compare).orElse(0.0);
//            case "sum" -> values.stream().mapToDouble(Double::doubleValue).sum();
//            case "average" -> values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
//            default -> throw new IllegalArgumentException("Invalid statistic");
//        };
//    }
//
//    public List<Sensor> getAllSensorData() {
//        return sensorDataRepository.findAll();
//    }
}