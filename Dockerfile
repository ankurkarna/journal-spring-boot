# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built jar file (update the name if needed)
COPY target/Journal-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default Spring Boot is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]