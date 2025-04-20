FROM openjdk:17-jdk-slim

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

COPY target/gateway-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app.jar"]
