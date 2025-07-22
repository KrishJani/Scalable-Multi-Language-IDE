FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/ide-java.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
