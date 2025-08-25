# Dockerfile
FROM eclipse-temurin:21

ARG JAR_FILE=target/CodeArena-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} CodeArena-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/CodeArena-0.0.1-SNAPSHOT.jar"]