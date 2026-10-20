# Stage 1: Build the Spring Boot application
FROM maven:3.9-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy Maven configuration and source code
COPY pom.xml .
COPY src ./src

# Compile and package the application
RUN mvn clean package -DskipTests


# Stage 2: Run the Spring Boot application
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the generated JAR from Stage 1
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]