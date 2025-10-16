package com.weather.metrics.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricsQuery {
    private List<String> sensorIds;


    private List<String> metricNames;

    @NonNull
    private String statistic;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
