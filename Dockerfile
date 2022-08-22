FROM openjdk:17-alpine3.14 AS builder

VOLUME /vol

ARG jar_file=build/libs/*.jar
ARG apm_agent=apm-agent/*.jar

COPY ${jar_file} app.jar

ENTRYPOINT ["java", \
"-Dspring.profiles.active=live", \
"-jar", \
"/app.jar"]
