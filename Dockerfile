# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS build

# Set the volume for the temporary directory
VOLUME /tmp

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Install the dependencies and build the application
RUN ./mvnw install -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Define build arguments
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG MAIL_USERNAME
ARG MAIL_PASSWORD
ARG CORS_ALLOWED_ORIGIN
ARG SPRING_PROFILES_ACTIVE

# Set environment variables from build arguments
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV CORS_ALLOWED_ORIGIN=${CORS_ALLOWED_ORIGIN}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
