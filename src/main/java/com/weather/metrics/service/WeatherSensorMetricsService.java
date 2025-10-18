package com.weather.metrics.service;


import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsResponse;
import com.weather.metrics.dto.QueryResults;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.exception.CustomExceptionResponse;
import com.weather.metrics.repository.SensorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
@Slf4j
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
                throw new CustomExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving sensor data: " + e.getMessage());
            }


        }

    public MetricsResponse querySensorData(MetricsQuery request) {
        try {
            // Validate date range
            validationService.validateDateRange(request);

            // Set default date range if not provided
            LocalDate endDate = request.getEndDate() != null ? request.getEndDate() : LocalDate.now();
            LocalDate startDate = request.getStartDate() != null ? request.getStartDate() : endDate.minusDays(1);
            // Fetch aggregated data based on query parameters
            List<QueryResults> aggregatedResults = sensorDataRepository.findAggMetrics(
                    request.getSensorIds(),
                    request.getMetricNames(),
                    startDate,
                    endDate,
                    request.getStatistic()
            );

            if (aggregatedResults.isEmpty()) {
                throw new RuntimeException(
                        "No sensor data found for the given for id's"+ request.getSensorIds() +" and metrics "+ request.getMetricNames());
            }

            //Form the response
            Map<String, Map<String, Double>> results = transformAggregationResults(aggregatedResults);

            MetricsResponse response = new MetricsResponse();
            response.setResults(results);
            return response;

        }catch (Exception e) {
            throw new CustomExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR,"An error occurred while querying sensor data: " + e.getMessage());
        }
    }

    private Map<String, Map<String, Double>> transformAggregationResults(List<QueryResults> aggregatedResults) {
        Map<String, Map<String, Double>> results = new HashMap<>();

        for (QueryResults result : aggregatedResults) {
            results.computeIfAbsent(result.getSensorId(), k -> new HashMap<>())
                    .put(result.getMetricName(), result.getResult());
        }

        return results;
    }

    public List<Sensor> getAllSensorData() {
        try{
            return sensorDataRepository.findAll();
        }
        catch (Exception e) {
            throw new CustomExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching all the sensor data: " + e.getMessage());
        }
    }

}
