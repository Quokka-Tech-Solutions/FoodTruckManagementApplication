FROM ubuntu:latest
LABEL authors="Quokka Tech"

FROM openjdk:17

COPY ./build/libs/FoodTruckManagementApplication-0.0.1-SNAPSHOT.jar foodtruckapp.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/foodtruckapp.jar"]