FROM openjdk:21
LABEL authors="dashavav"
COPY ./target/scrapper.jar scrapper.jar

ENTRYPOINT ["java", "-jar", "/scrapper.jar"]
