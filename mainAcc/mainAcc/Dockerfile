FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java","-jar","/payment-service.jar"]
EXPOSE 9003