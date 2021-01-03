# Step : Test and package
FROM maven:3.6.3-jdk-8 as target

WORKDIR /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/

RUN mvn dependency:go-offline

COPY src /build/src
RUN mvn package

# Step : Package image
FROM openjdk:8-jre-alpine
RUN apk update && apk add bash


CMD exec java $JAVA_OPTS -jar /app/my-app.jar
COPY --from=target /tmp/target/*.jar /app/my-app.jar