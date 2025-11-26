# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

# Salin dulu pom.xml supaya dependency bisa di-cache
COPY pom.xml .

RUN mvn -B dependency:go-offline

# Baru salin source code
COPY src ./src

# Build jar (opsional: tambahkan -DskipTests jika mau skip test)
RUN mvn -B clean package

# ---------- STAGE 2: RUNTIME ----------
FROM eclipse-temurin:17-jre-alpine AS runner
# kalau mau lebih kompatibel: eclipse-temurin:17-jre-jammy / slim

WORKDIR /app

# Salin jar hasil build dari stage builder
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "app.jar"]
