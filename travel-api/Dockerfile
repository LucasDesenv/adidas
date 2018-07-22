FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 7111
ARG JAR_FILE
COPY ${JAR_FILE} travel-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/travel-api.jar"]