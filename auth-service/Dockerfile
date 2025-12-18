# ---------- STAGE 1: BUILD ----------
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder

WORKDIR /app

# Salin dulu pom.xml supaya dependency bisa di-cache
COPY pom.xml .

RUN mvn -B dependency:go-offline

# Baru salin source code
COPY src ./src

RUN mvn -B clean package -DskipTests

# ---------- STAGE 2: RUNTIME ----------
FROM eclipse-temurin:21-jre-jammy AS runner
# kalau mau lebih kompatibel: eclipse-temurin:17-jre-jammy / slim

WORKDIR /app

# Salin jar hasil build dari stage builder
COPY --from=builder /app/target/auth-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4005

ENTRYPOINT ["java", "-jar", "app.jar"]
