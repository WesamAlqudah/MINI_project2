FROM java:8

EXPOSE 8080

ADD target/MiniProject2.jar MiniProject2.jar

ENTRYPOINT ["java","-jar","MiniProject2.jar", "--spring.profiles.active=docker"]