# Build Stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copia apenas os arquivos necessários para o build
COPY ./demoApp/pom.xml .
COPY ./demoApp/src ./src

# Executa o build do Maven
RUN mvn clean install -U

# Runtime Stage
FROM openjdk:21
WORKDIR /app

# Copia apenas o JAR necessário
COPY --from=build /app/target/demoApp-0.0.1-SNAPSHOT.jar /app/app.jar
COPY ./demoApp/.env /app/.env

# Cria o diretório para o volume mas não copia dados iniciais
RUN mkdir -p /app/database

# Define o volume para persistência
VOLUME /app/database
EXPOSE 8080

# Executa a aplicação
CMD ["java", "-jar", "app.jar"]