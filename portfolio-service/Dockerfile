# Stage 1: Build
FROM maven:3.9.11-eclipse-temurin-21 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and dependencies first (to leverage Docker cache)
COPY pom.xml .

# Download dependencies

RUN mvn dependency:resolve

# Copy the rest of the application source code

COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
