# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

# Copia el archivo .jar generado por Spring Boot
# Ver nombre del .jar generado al ejecutar maven install*/
COPY target/Challenge-Monitoreo-0.0.1-SNAPSHOT.jar /app.jar  

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar el backend
ENTRYPOINT ["java", "-jar", "/app.jar"]