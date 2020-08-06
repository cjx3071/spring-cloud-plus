FROM java:8
ARG workdir=/data/logs
VOLUME ${workdir}
WORKDIR ${workdir}
ARG JAR_FILE
COPY ${JAR_FILE} sub-web.jar
EXPOSE 10002
ENTRYPOINT ["java","-jar","sub-web.jar"]