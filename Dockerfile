FROM openjdk:17-jdk-slim

COPY target/messenger-0.0.1-SNAPSHOT.jar app.jar

WORKDIR /the/workdir/path

ENTRYPOINT [ "java" , "-jar" , "/app.jar"]


