FROM openjdk:25-jdk-slim
MAINTAINER Bart van Pelt <brtvnplt@gmail.com>
# Or a suitable base image

ARG JAR_FILE

COPY target/${JAR_FILE} /app.jar

EXPOSE 8080
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]