# 1. Use Maven to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
  
  # Copy pom.xml and source code
COPY pom.xml .
COPY src ./src
  
  # Package the application (skip tests for faster build)
RUN mvn clean package -DskipTests
  
  # 2. Use lightweight JDK image for runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
  
  # Copy only the jar file from build stage
COPY --from=build /app/target/*.jar app.jar
  
  # Expose port (matches your Spring Boot server.port)
EXPOSE 8081
  
  # Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
