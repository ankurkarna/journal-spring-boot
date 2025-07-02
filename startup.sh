#!/bin/sh

echo "Starting Journal Application with production profile..."
echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"

# Start the application with optimized settings
exec java $JAVA_OPTS -jar app.jar 