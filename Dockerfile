# Steg 1: Byggstadiet ( kompilerar spring boot API)

FROM maven:3.9-eclipse-temurin-17-alpine as build

WORKDIR /app

copy pom.xml .
copy src ./src

RUN mvn clean package -DskipTests

# Steg 2: Exekvering (produktionsmiljö)

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/hotel-booking-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

