FROM openjdk:8u181-jdk-alpine
ARG workdir=/data/sub-web
VOLUME ${workdir}
WORKDIR ${workdir}
ARG JAR_FILE
COPY ${JAR_FILE} sub-web.jar
EXPOSE 10002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","sub-web.jar"]