FROM openjdk:19
LABEL maintainer="jacek626@gmail.com"
VOLUME /main-app
ADD target/CmsApi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]