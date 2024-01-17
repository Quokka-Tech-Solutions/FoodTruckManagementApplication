# syntax=docker/dockerfile:1
FROM ubuntu:latest
FROM openjdk:17

WORKDIR /src/main/java

# CMD ["./gradlew", "clean", "bootjar"]
COPY build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]