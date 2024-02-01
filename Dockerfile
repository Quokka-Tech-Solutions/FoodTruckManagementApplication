# syntax=docker/dockerfile:1
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the Gradle files for dependency resolution
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew clean bootJar

# Copy the JAR file
COPY build/libs/order-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "/app/app.jar"]

