FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY target/api-marketplace.jar marketplace.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","marketplace.jar"]
