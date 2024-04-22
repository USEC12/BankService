
FROM openjdk:11-jdk-slim


WORKDIR /app

COPY ./target/bank-services.jar /app/bank-services.jar


EXPOSE 4545


CMD ["java", "-jar", "bank-services.jar"]
