FROM openjdk:17-jdk-slim-buster
ARG JAR_FILE=target/apiclient-spring-boot-boilerplate.jar
WORKDIR /opt/app
COPY target/apiclient-spring-boot-boilerplate-1.0.0.jar /apiclient-spring-boot-boilerplate-docker.jar
ENTRYPOINT ["java","-jar","/apiclient-spring-boot-boilerplate-docker.jar"]