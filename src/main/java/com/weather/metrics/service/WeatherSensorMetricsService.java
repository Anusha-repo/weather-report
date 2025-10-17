package com.weather.metrics.service;


import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsResponse;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.exception.CustomExceptionResponse;
import com.weather.metrics.repository.SensorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import com.weather.metrics.constants.AppConstants.Statistics;
@Service
public class WeatherSensorMetricsService {

    @Autowired
    private SensorRepo sensorDataRepository;

    @Autowired
    ValidationService validationService;

    public Sensor saveSensorData(Sensor sensorData) {
            try{
                if (sensorData.getTimestamp() == null) {
                    sensorData.setTimestamp(LocalDate.now());
                }
                return sensorDataRepository.save(sensorData);

            }catch (Exception e) {
                throw new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to save sensor data"+e.getMessage());
            }


        }


    public MetricsResponse querySensorData(MetricsQuery request) {
        try {
            // Validate date range
            validationService.validateDateRange(request);

            // Set default date range if not provided (last 24 hours)
            LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : LocalDate.now();
            LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : endDate.minusDays(1);

            // Fetch data based on query parameters
            List<Sensor> sensorDataList = fetchSensorData(request, startDate, endDate);
            if (sensorDataList.isEmpty()) {
                throw new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No sensor data found for the given query parameters");

            }

            // Calculate statistics
            Map<String, Map<String, Double>> results = calculateStatistics(sensorDataList, request);

            MetricsResponse response = new MetricsResponse();
            response.setResults(results);
            response.setMessage("Query executed successfully");

            return response;
        } catch (DataAccessException e) {
        throw new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Database error while querying sensor data. " + e.getMessage());
    }

    }

    private List<Sensor> fetchSensorData(MetricsQuery request, LocalDate startDate, LocalDate endDate) {
        List<String> sensorIds = request.getSensorIds();
        List<String> metrics = request.getMetricNames();

        if (sensorIds != null && !sensorIds.isEmpty()) {
            return sensorDataRepository.findMetrics(sensorIds, metrics, startDate, endDate);
        } else {
            return sensorDataRepository.findMetrics(null,metrics, startDate, endDate);
        }
    }

    private Map<String, Map<String, Double>> calculateStatistics(List<Sensor> sensorDataList, MetricsQuery request) {
        // Group by sensor and metric
        Map<String, Map<String, List<Double>>> groupedData = sensorDataList.stream()
                .collect(Collectors.groupingBy(
                        Sensor::getSensorId,
                        Collectors.groupingBy(
                                Sensor::getMetricName,
                                Collectors.mapping(Sensor::getMetricValue, Collectors.toList())
                        )
                ));

        Map<String, Map<String, Double>> results = new HashMap<>();

        for (Map.Entry<String, Map<String, List<Double>>> sensorEntry : groupedData.entrySet()) {
            String sensorId = sensorEntry.getKey();
            Map<String, List<Double>> metricData = sensorEntry.getValue();

            Map<String, Double> sensorResults = new HashMap<>();

            for (Map.Entry<String, List<Double>> metricEntry : metricData.entrySet()) {
                String metric = metricEntry.getKey();
                List<Double> values = metricEntry.getValue();

                if (values.isEmpty()) continue;

                double result = calculateStatistic(values, request.getStatistic());
                sensorResults.put(metric, result);
            }

            if (!sensorResults.isEmpty()) {
                results.put(sensorId, sensorResults);
            }
        }

        return results;
    }

    private double calculateStatistic(List<Double> values, String statistic) {
        try {
            return switch (statistic) {
                case Statistics.MIN -> values.stream().min(Double::compare).orElse(0.0);
                case Statistics.MAX -> values.stream().max(Double::compare).orElse(0.0);
                case Statistics.SUM -> values.stream().mapToDouble(Double::doubleValue).sum();
                case Statistics.AVG -> values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                default -> throw new IllegalArgumentException("Invalid statistic");
            };
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error calculating statistic: " + e.getMessage(), e);
        }
    }


    public List<Sensor> getAllSensorData() {
        try{
            return sensorDataRepository.findAll();
        }catch (DataAccessException e) {
            throw new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch all sensor data: from SENSOR table. " + e.getMessage());
        }
    }

}
