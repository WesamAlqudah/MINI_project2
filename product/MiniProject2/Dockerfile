#FROM java:8
#
#EXPOSE 0
#
#ADD target/MiniProject2.jar MiniProject2.jar
#
#ENTRYPOINT ["java","-jar","MiniProject2.jar"]

FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} product-service.jar
ENTRYPOINT ["java","-jar","/product-service.jar"]
EXPOSE 9001s