# weather-report
# curl to save sensor data:
curl --location --request POST 'http://localhost:8080/api/v1/weather-metrics/save' \
--header 'Content-Type: application/json' \
--data-raw '{
"sensorId":"sensor-1",
"metricName":"temperature",
"metricValue":30.0
}'

# curl to query sensor data (single values):
curl --location --request POST 'http://localhost:8080/api/v1/weather-metrics/query' \
--header 'Content-Type: application/json' \
--data-raw '{
"sensorIds":["sensor-1"],
"metricName":["temperature"],
"statistic": "AVG"
}'

# curl to query sensor data (multiple values):
curl --location --request POST 'http://localhost:8080/api/v1/weather-metrics/query' \
--header 'Content-Type: application/json' \
--data-raw '{
"sensorIds":["sensor-6", "sensor-1"],
"metricNames":["humidity","temperature"],
"statistic": "MAX"
}'

# curl to fetch all results in the DB:
curl --location --request GET 'http://localhost:8080/api/v1/weather-metrics/allSensorData' \
--header 'Content-Type: application/json' \
--data-raw ''

# Valid statistic values are: AVG, MIN, MAX, SUM, COUNT

## Enhancements
# JWT based authentication for secure access to the API endpoints.
# @PreAuthorise annotations on controller methods to restrict access based on user roles.
# Swagger integration for API documentation and testing.


