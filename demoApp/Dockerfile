# Build Stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY ./demoApp /app
COPY ./demoApp/.env /app/.env

# Executa o build do Maven
RUN mvn clean install -U

# Runtime Stage
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/demoApp-0.0.1-SNAPSHOT.jar /app/app.jar
COPY ./demoApp/.env /app/.env
CMD ["java", "-jar", "app.jar"]