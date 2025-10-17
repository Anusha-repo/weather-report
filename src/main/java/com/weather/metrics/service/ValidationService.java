package com.weather.metrics.service;

import com.weather.metrics.dto.MetricsQuery;
import org.springframework.stereotype.Component;
import java.time.temporal.ChronoUnit;


@Component
public class ValidationService {

    public void validateDateRange(MetricsQuery request) {
        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getStartDate().isAfter(request.getEndDate())) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }

            // Check if date range is between 1 day and 1 month
            long daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
            if (daysBetween < 1 || daysBetween > 31) {
                throw new IllegalArgumentException("Date range must be between 1 day and 1 month");
            }
        }
    }


}
