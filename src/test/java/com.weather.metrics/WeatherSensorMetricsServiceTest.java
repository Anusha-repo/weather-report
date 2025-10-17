package com.weather.metrics;

import com.weather.metrics.dto.MetricsQuery;
import com.weather.metrics.dto.MetricsRequest;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.exception.CustomExceptionResponse;
import com.weather.metrics.repository.SensorRepo;
import com.weather.metrics.service.WeatherSensorMetricsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WeatherSensorMetricsServiceTest {

    @Mock
    private SensorRepo sensorRepository;

    @InjectMocks
    private WeatherSensorMetricsService sensorService;



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
        assertTrue(exception.getMessage().contains("Unable to save sensor data"));
        verify(sensorRepository, times(1)).save(mockSensorData);
    }


}
