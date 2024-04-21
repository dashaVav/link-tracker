FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/scrapper.jar scrapper.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -jar scrapper.jar
