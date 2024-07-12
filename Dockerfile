# Stage 1: Build stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN ls -l /app/target

# Stage 2: Runtime stage
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

RUN ls -l /app

ENTRYPOINT ["java", "-jar", "app.jar"]
