# syntax=docker/dockerfile:1
FROM ubuntu:latest
FROM openjdk:17

WORKDIR /src/main/java

CMD ["./gradlew", "clean", "bootjar"]
COPY /build/libs/order-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]
