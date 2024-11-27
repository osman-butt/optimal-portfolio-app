# Stage 1: Build
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and dependencies first (to leverage Docker cache)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Use an environment variable for additional Java options (optional)
ENV JAVA_OPTS=""

# Command to run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
