FROM openjdk:21
LABEL authors="dashavav"
COPY ./target/bot.jar bot.jar
ENV TELEGRAM_TOKEN=${TELEGRAM_TOKEN}

ENTRYPOINT ["java", "-jar", "/bot.jar"]

