# Stage 1: Use Maven for building
FROM maven:3.8.8 AS build
WORKDIR /app


COPY ./notificationservice ./notificationservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install package -DskipTests


WORKDIR /app/notificationservice
RUN mvn clean package -DskipTests

# Stage 2: Use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/notificationservice/target/*.jar notificationservice.jar
EXPOSE 9003
ENTRYPOINT ["java","-jar","notificationservice.jar"]