# dwpTest

How to start the dwpTest application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/dwp-test-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`


## Test running

### Integration

mvn verify 

Trying to run mvn integration-test directly will result in errors due to issues with jetty containers (see https://maven.apache.org/surefire/maven-failsafe-plugin/usage.html Using jetty and maven-failsafe-plugin)




## ATTRIBUTIONS:

CITY to Lat/Long Databse provided by https://simplemaps.com/data/world-cities.  

