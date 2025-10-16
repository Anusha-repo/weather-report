package com.weather.metrics.dto;


import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricsRequest {

    @NonNull
    private String sensorId;

    @NonNull
    private String metricName;

    @NonNull
    private Double metricValue;


    private LocalDateTime timestamp;


}
