# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app

# Cache dependency
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source
COPY src ./src

# Build
RUN mvn -B clean package -DskipTests


# ---------- STAGE 2: RUNTIME (DEBUG: JDK) ----------
FROM eclipse-temurin:21-jdk-jammy AS runner
WORKDIR /app

COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 4004
ENTRYPOINT ["java","-jar","/app/app.jar"]
