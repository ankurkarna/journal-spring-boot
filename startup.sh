#!/bin/sh

echo "Starting Journal Application with production profile..."

# Set default JVM options if not provided
if [ -z "$JAVA_OPTS" ]; then
    JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:+UseContainerSupport -Dspring.profiles.active=prod"
fi

# Set default Spring profile if not provided
if [ -z "$SPRING_PROFILES_ACTIVE" ]; then
    SPRING_PROFILES_ACTIVE="prod"
fi

echo "JAVA_OPTS: $JAVA_OPTS"
echo "SPRING_PROFILES_ACTIVE: $SPRING_PROFILES_ACTIVE"

# Start the application with optimized settings
exec java $JAVA_OPTS -jar app.jar 