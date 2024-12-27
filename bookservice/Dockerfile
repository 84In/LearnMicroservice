# Stage 1: Use Maven for building
FROM maven:3.8.8 AS build
WORKDIR /app


COPY ./bookservice ./bookservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install package -DskipTests

WORKDIR /app/bookservice
RUN mvn clean package -DskipTests

# Stage 2: Use OpenJDK for running
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","bookservice.jar"]