# Stage 1: Build
FROM openjdk:21-jdk-slim AS builder

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

WORKDIR /app
COPY . .

# If you're using a parent POM or multi-module project, add -pl or -am as needed
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:21-jdk-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
