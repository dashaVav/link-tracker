FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/scrapper.jar .
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app/scrapper.jar"]