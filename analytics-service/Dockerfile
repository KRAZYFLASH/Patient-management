# Dockerfile
# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- STAGE 2: RUNTIME ----------
FROM eclipse-temurin:21-jre-jammy AS runner
WORKDIR /app

COPY --from=builder /app/target/analytics-service-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 4002
ENTRYPOINT ["java", "-jar", "app.jar"]
