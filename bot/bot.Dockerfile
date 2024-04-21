FROM openjdk:21-jdk-slim

COPY ./target/bot.jar bot.jar
ENV TELEGRAM_TOKEN=${TELEGRAM_TOKEN}

ENTRYPOINT ["java", "-jar", "/bot.jar"]

