FROM openjdk:8
COPY . .
EXPOSE 8082
CMD ["java", "-jar", "target/a4-1.0-SNAPSHOT-jar-with-dependencies.jar"]