FROM openjdk:8-jdk-alpine

EXPOSE 8080

COPY target/*.jar app.jar

ENTRYPOINT exec java -jar app.jar

