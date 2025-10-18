# weather-report (Java Spring Boot Application)

## Steps:
1. JDK version used: 17 <br />
2. Repo link: https://github.com/Anusha-repo/weather-report <br />
3. To run the app from terminal:
    mvn clean install 	  (builds the Maven project and installs the project files and excute the tescases)
    mvn spring-boot:run   (run the app from the terminal)
4. To run the tests from terminal:
   mvn test
5. Valid statistic values are: AVG, MIN, MAX, SUM, COUNT
6. API Endpoints:
   a. POST /api/v1/weather-metrics/save : To save sensor data
   b. POST /api/v1/weather-metrics/query : To query sensor data based on sensorIds, metricNames, statistic, startDate, endDate
   c. GET /api/v1/weather-metrics/allSensorData : To fetch all sensor data stored in the database
7. In memory H2 database is used for storing sensor data. 
8. Date is an optional field while saving the sensor data. If not provided, current date will be considered for save and last 24 hours data will be fetched.
9. Use the below curl's to test the API endpoints. First use the curl to save sensor data before querying it.


## curl to save sensor data:
curl --location --request POST 'http://localhost:8080/api/v1/weather-metrics/save' \
--header 'Content-Type: application/json' \
--data-raw '{
"sensorId":"sensor-3",
"metricName":"humidity",
"metricValue":19.9,
"timestamp": "2025-10-17" //optional
}'

## curl to query sensor data :
curl --location --request POST 'http://localhost:8080/api/v1/weather-metrics/query' \
--header 'Content-Type: application/json' \
--data-raw '{
"sensorIds":["sensor-3","sensor-2"],
"metricNames":["humidity","tempearture"],
"statistic": "MIN",
"startDate": "2025-10-15", // optional
"endDate": "2025-10-17"
}'

## curl to fetch all results in the DB:
curl --location --request GET 'http://localhost:8080/api/v1/weather-metrics/allSensorData' \
--header 'Content-Type: application/json' \
--data-raw ''


## Enhancements
1. JWT based authentication for secure access to the API endpoints.
2. @PreAuthorise annotations on controller methods to restrict access based on user roles.
3. Swagger integration for API documentation and testing.
4. Include more unit tests to increase the test coverage
5. Currently using slf4j simple logger, can be replaced with logback for better logging management. Also there is need to increase the logging for better visibility.

 


