package com.weather.metrics.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetricsQuery {
    private List<String> sensorIds;

    @NotNull(message = "Metric names list must not be null")
    @Size(min = 1, message = "Metric names list must contain at least one element")
    @Valid
    private List<String> metricNames;

    @NotBlank(message = "Statistic input cannot be null")
    @Pattern(regexp = "(AVG|MIN|MAX|SUM|COUNT)", message = "Statistic input must be one of the following: AVG, MIN, MAX, SUM, COUNT")
    private String statistic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
