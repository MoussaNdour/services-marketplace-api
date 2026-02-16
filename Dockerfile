FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY /api-marketplace.jar marketplace.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","marketplace.jar"]
