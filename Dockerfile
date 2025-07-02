# Simple, fast Dockerfile for Render
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Install wget for health check
RUN apk add --no-cache wget

# Copy the JAR file (will be built by Render)
COPY target/*.jar app.jar

# Simple health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

EXPOSE 8080

# Simple JVM options - minimal and safe
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Start the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]