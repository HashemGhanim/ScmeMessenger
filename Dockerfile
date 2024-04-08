# FROM openjdk:17-jdk-slim

# COPY target/messenger-0.0.1-SNAPSHOT.jar app.jar

# WORKDIR /the/workdir/path

# ENTRYPOINT [ "java" , "-Dspring.profiles.active=test" ,"-jar" , "/app.jar"]


FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/messenger-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=test" , "-jar","demo.jar"]