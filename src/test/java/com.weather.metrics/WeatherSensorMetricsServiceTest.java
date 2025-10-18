package com.weather.metrics;

import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsResponse;
import com.weather.metrics.dto.QueryResults;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.exception.CustomExceptionResponse;
import com.weather.metrics.repository.SensorRepo;
import com.weather.metrics.service.ValidationService;
import com.weather.metrics.service.WeatherSensorMetricsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherSensorMetricsServiceTest {

    @Mock
    private SensorRepo sensorRepository;

    @InjectMocks
    private WeatherSensorMetricsService sensorService;

    @Mock
    private ValidationService validationService;




    private Sensor createMockSensorData() {
        return Sensor.builder()
                .sensorId("sensor-1")
                .metricName("temperature")
                .metricValue(19.5)
                .timestamp(LocalDate.now())
                .build();
    }

    private Sensor createMockSavedSensorData() {
        return Sensor.builder()
                .sensorId("sensor-1")
                .metricName("temperature")
                .metricValue(19.5)
                .timestamp(LocalDate.now())
                .build();
    }

    @Test
    void testSaveSensorData_Success() {


        Sensor mockSensorData = createMockSensorData();
        Sensor mockSavedSensorData = createMockSavedSensorData();

        when(sensorRepository.save(mockSensorData)).thenReturn(mockSavedSensorData);

        Sensor result = sensorService.saveSensorData(mockSensorData);

        // Assert
        assertNotNull(result);
        assertEquals(mockSensorData, result);
        verify(sensorRepository, times(1)).save(mockSensorData);
    }

    @Test
    void testSaveSensorData_Failure() {


        Sensor mockSensorData = createMockSensorData();

        when(sensorRepository.save(mockSensorData)).thenThrow(new RuntimeException("Database error"));

        CustomExceptionResponse exception = assertThrows(CustomExceptionResponse.class, () -> {
            sensorService.saveSensorData(mockSensorData);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertTrue(exception.getMessage().contains("An error occurred while saving sensor data:"));
        verify(sensorRepository, times(1)).save(mockSensorData);
    }

    @Test
    void testQuerySensorData_Success() {
        // Arrange
        MetricsQuery request = new MetricsQuery();
        request.setSensorIds(List.of("sensor-1"));
        request.setMetricNames(List.of("temperature"));
        request.setStatistic("AVG");

        QueryResults mockResult = mock(QueryResults.class);
        when(mockResult.getSensorId()).thenReturn("sensor-1");
        when(mockResult.getMetricName()).thenReturn("temperature");
        when(mockResult.getResult()).thenReturn(25.0);

        when(sensorRepository.findAggMetrics(anyList(), anyList(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(List.of(mockResult));

        // Act
        MetricsResponse response = sensorService.querySensorData(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getResults().size());
        assertEquals(25.0, response.getResults().get("sensor-1").get("temperature"));
        verify(validationService, times(1)).validateDateRange(request);
        verify(sensorRepository, times(1)).findAggMetrics(anyList(), anyList(), any(LocalDate.class), any(LocalDate.class), anyString());
    }

    @Test
    void testQuerySensorData_NoDataFound() {
        // Arrange
        MetricsQuery request = new MetricsQuery();
        request.setSensorIds(List.of("sensor-1"));
        request.setMetricNames(List.of("temperature"));
        request.setStatistic("AVG");

        when(sensorRepository.findAggMetrics(anyList(), anyList(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(List.of());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> sensorService.querySensorData(request));
        assertEquals("An error occurred while querying sensor data: No sensor data found for the given for id's[sensor-1] and metrics [temperature]", exception.getMessage());
        verify(validationService, times(1)).validateDateRange(request);
        verify(sensorRepository, times(1)).findAggMetrics(anyList(), anyList(), any(LocalDate.class), any(LocalDate.class), anyString());
    }



}
