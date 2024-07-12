FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /easy-travel
COPY . /easy-travel
RUN mvn clean package -DskipTests
RUN ls -l /easy-travel/target

FROM openjdk:17.0.1-jdk-slim
WORKDIR /easy-travel
COPY --from=build /easy-travel/target/*.jar app.jar

RUN ls -l /app

ENTRYPOINT ["java", "-jar", "app.jar"]
