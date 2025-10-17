package com.weather.metrics;

import com.weather.metrics.controller.WeatherMetricsController;
import com.weather.metrics.dto.MetricsRequest;
import com.weather.metrics.entity.Sensor;
import com.weather.metrics.service.WeatherSensorMetricsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherMetricsControllerTest {
    @InjectMocks
    private WeatherMetricsController weatherSensorMetricsController;

    @Mock
    private WeatherSensorMetricsService weatherMetricsService;

    @Autowired
    private MockMvc mockMvc;


    private MetricsRequest createMetricsRequest() {
        return MetricsRequest.builder()
                .sensorId("sensor-1")
                .metricName("temperature")
                .metricValue(19.5)
                .timestamp(LocalDate.now())
                .build();
    }

    private Sensor createMockSensor() {
        return Sensor.builder()
                .id(1L)
                .sensorId("sensor-1")
                .metricName("temperature")
                .metricValue(19.5)
                .timestamp(LocalDate.now())
                .build();
    }

    @Test
    void testSaveSensorData() {

        MetricsRequest request = createMetricsRequest();
        Sensor mockSensor = createMockSensor();

        when(weatherMetricsService.saveSensorData(any(Sensor.class))).thenReturn(mockSensor);

        // Act
        ResponseEntity<Sensor> response = weatherSensorMetricsController.createSensorData(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("sensor-1", response.getBody().getSensorId());
        assertEquals("temperature", response.getBody().getMetricName());
        assertEquals(19.5, response.getBody().getMetricValue());
        verify(weatherMetricsService, times(1)).saveSensorData(any(Sensor.class));
    }


    @Test
    void testNullSensorIdThrowsException() throws Exception {

        String invalidRequest = """
    {
        "sensorId": null,
        "metricName": "temperature",
        "metricValue": 20.0,
        "timestamp": "2025-10-16"
    }
    """;

        mockMvc.perform(post("/api/v1/weather-metrics/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(weatherMetricsService, never()).saveSensorData(any(Sensor.class));
    }

    @Test
    void testNullMetricNameThrowsException() throws Exception {

        String invalidRequest = """
    {
        "sensorId": "sensor-1",
        "metricName": "",
        "metricValue": 20.0,
        "timestamp": "2025-10-16"
    }
    """;

        mockMvc.perform(post("/api/v1/weather-metrics/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(weatherMetricsService, never()).saveSensorData(any(Sensor.class));
    }

}
