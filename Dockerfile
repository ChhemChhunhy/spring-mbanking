# Builder stage
FROM gradle:8.4-jdk17-alpine AS builder
WORKDIR /app
COPY . .
# Build the application
RUN gradle build --no-daemon

# Final stage
FROM openjdk:17-alpine
WORKDIR /app
# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/mbaking-api-0.0.1-SNAPSHOT.jar /app/
EXPOSE 8080
VOLUME /home/images
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/mbaking-api-0.0.1-SNAPSHOT.jar"]
