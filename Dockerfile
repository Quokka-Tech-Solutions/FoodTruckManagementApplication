# syntax=docker/dockerfile:1
FROM ubuntu:latest
FROM openjdk:17

COPY build/libs/order-0.0.1-SNAPSHOT.jar /order.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/order.jar"]
