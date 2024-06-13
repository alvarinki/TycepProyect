FROM amazoncorretto:21-alpine-jdk
ARG JAR_FILE=jar/*.jar
COPY ${JAR_FILE} app.jar
CMD apt-get update -y
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/app.jar"]
