FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/bot.jar .
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app/bot.jar"]

