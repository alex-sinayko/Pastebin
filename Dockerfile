FROM openjdk:17-alpine
COPY build/libs/Pastebin-1.0-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT exec java -jar /app.jar