FROM openjdk:8-jdk-alpine
MAINTAINER Vladislav Solodkiy <vsolodkiy.dev@ya.ru>
ARG JAR_FILE=target/homework1-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
