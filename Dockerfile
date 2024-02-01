# syntax=docker/dockerfile:1
FROM ubuntu:latest
FROM openjdk:17

RUN mkdir /app
COPY ./build/libs/order-0.0.1-SNAPSHOT.jar /app/order.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/order.jar"]
