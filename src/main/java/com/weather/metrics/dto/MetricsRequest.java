package com.weather.metrics.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricsRequest {

    @NotBlank(message = "Sensor ID cannot be null")
    private String sensorId;

    @NotBlank(message = "MetricName cannot be null")
    private String metricName;

    @NotNull(message = "Metric value must not be null")
    @Positive(message = "Metric value must be positive")
    private Double metricValue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate timestamp;


}
