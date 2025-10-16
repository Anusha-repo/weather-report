//package com.weather.metrics.service;
//
//import com.weather.metrics.entity.Metrics;
//import com.weather.metrics.entity.Sensor;
//import com.weather.metrics.repository.MetricsRepo;
//import com.weather.metrics.repository.SensorRepo;
//import com.weather.metrics.dto.MetricsQuery;
//import com.weather.metrics.dto.MetricsRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class WeatherMetricsService {
//    @Autowired
//    private MetricsRepo metricRepository;
//
//    @Autowired
//    private SensorRepo sensorRepository;
//
//    public void addMetric(MetricsRequest metricRequest) {
//        Sensor sensor = sensorRepository.findById(metricRequest.getSensorId())
//                .orElseThrow(() -> new IllegalArgumentException("Sensor not found"));
//
//        Metrics metric = new Metrics();
//        metric.setSensorId(sensor);
//        metric.setName(metricRequest.getName());
//        metric.setValue(metricRequest.getValue());
//        metric.setTimestamp(metricRequest.getTimestamp());
//
//        metricRepository.save(metric);
//    }
//
//    public List<Map<String, Object>>  queryMetrics(MetricsQuery metricQuery) {
//
//        LocalDateTime startDate = metricQuery.getStartDate() != null ? metricQuery.getStartDate() : LocalDateTime.now().minusDays(1);
//        LocalDateTime endDate = metricQuery.getEndDate() != null ? metricQuery.getEndDate() : LocalDateTime.now();
//
//        List<Metrics> metrics = metricRepository.findMetrics(
//                metricQuery.getSensorIds(),
//                metricQuery.getMetricNames(),
//                startDate,
//                endDate
//        );
//
//
//            return switch (metricQuery.getStatistic()) {
//                case "min" -> metrics.stream()
//                        .collect(Collectors.groupingBy(m -> Map.of("sensorId", m.getSensorId(), "metricName", m.getName()),
//                                Collectors.minBy((m1, m2) -> Double.compare(m1.getValue(), m2.getValue()))))
//                        .entrySet().stream()
//                        .map(entry -> Map.of(
//                                "sensorId", entry.getKey().get("sensorId"),
//                                "metricName", entry.getKey().get("metricName"),
//                                "statistic", entry.getValue().map(Metrics::getValue).orElse(null)))
//                        .toList();
//                case "max" -> metrics.stream()
//                        .collect(Collectors.groupingBy(m -> Map.of("sensorId", m.getSensorId(), "metricName", m.getName()),
//                                Collectors.maxBy((m1, m2) -> Double.compare(m1.getValue(), m2.getValue()))))
//                        .entrySet().stream()
//                        .map(entry -> Map.of(
//                                "sensorId", entry.getKey().get("sensorId"),
//                                "metricName", entry.getKey().get("metricName"),
//                                "statistic", entry.getValue().map(Metrics::getValue).orElse(null)))
//                        .toList();
//                case "sum" -> metrics.stream()
//                        .collect(Collectors.groupingBy(m -> Map.of("sensorId", m.getSensorId(), "metricName", m.getName()),
//                                Collectors.summingDouble(Metrics::getValue)))
//                        .entrySet().stream()
//                        .map(entry -> Map.of(
//                                "sensorId", entry.getKey().get("sensorId"),
//                                "metricName", entry.getKey().get("metricName"),
//                                "statistic", entry.getValue()))
//                        .toList();
//                case "average" -> metrics.stream()
//                        .collect(Collectors.groupingBy(m -> Map.of("sensorId", m.getSensorId(), "metricName", m.getName()),
//                                Collectors.averagingDouble(Metrics::getValue)))
//                        .entrySet().stream()
//                        .map(entry -> Map.of(
//                                "sensorId", entry.getKey().get("sensorId"),
//                                "metricName", entry.getKey().get("metricName"),
//                                "statistic", entry.getValue()))
//                        .toList();
//                default -> throw new IllegalArgumentException("Invalid statistic");
//            };
//    }
//}
