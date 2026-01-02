FROM openjdk:26-trixie

ADD target/api-marketplace.jar marketplace.jar

ENTRYPOINT ["java", "-jar","api-marketplace.jar"]