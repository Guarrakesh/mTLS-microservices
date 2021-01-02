FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring

RUN apk update && apk add bash

USER spring:spring


ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# ENTRYPOINT ["tail", "-f", "/dev/null"]

ENTRYPOINT ["java","-cp","app:app/lib/*","hello.Application"]
