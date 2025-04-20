# Usar la imagen oficial de OpenJDK como base
FROM openjdk:17-jdk-slim

# Setear la variable de entorno para que Spring Boot loguee en consola
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# Copiar el archivo jar generado por Maven (asegúrate que el nombre del archivo es correcto)
COPY target/productos-service-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que Spring Boot corre
EXPOSE 8081

# Ejecutar el archivo jar con el comando Java
ENTRYPOINT ["java", "-jar", "/app.jar"]
