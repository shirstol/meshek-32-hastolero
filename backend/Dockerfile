# Build the Spring Boot application in a reproducible Linux environment.
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# Run only the small JRE image in production.
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# Render supplies PORT at runtime; 8080 remains the local default.
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
