# -------- Stage 1: Build the JAR --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml and download dependencies first (for caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Package the app
RUN mvn clean package -DskipTests

# -------- Stage 2: Run the JAR --------
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/expense_report-0.0.1-SNAPSHOT.war app.war

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.war"]
