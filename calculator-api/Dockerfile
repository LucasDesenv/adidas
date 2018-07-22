FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8087
ARG JAR_FILE
COPY ${JAR_FILE} calculator-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/calculator-api.jar"]