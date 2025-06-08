# Imagen base con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos necesarios
COPY pom.xml .
COPY src ./src

# Ejecutar build
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM eclipse-temurin:17-jdk-jammy

# Copiar el .jar generado desde la fase anterior
COPY --from=build /app/target/Challenge-Monitoreo-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar el backend
ENTRYPOINT ["java", "-jar", "/app.jar"]
