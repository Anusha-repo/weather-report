package com.weather.metrics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricsResponse {
    // sensorId -> (metric -> value)
    private Map<String, Map<String, Double>> results;


}
