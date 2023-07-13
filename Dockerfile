FROM openjdk:17-jdk-slim

WORKDIR /user/local/app

COPY /build .

EXPOSE 8080

CMD ["java", "-jar", "/user/local/app/libs/jira-copy-0.0.1-SNAPSHOT.jar"]

