
# Etapa 1: Compilacion con Java 21
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecucion con Java 21
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/SistemaMascotasJava-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]