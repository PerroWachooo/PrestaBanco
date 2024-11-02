FROM openjdk:17
ARG JAR_FILE=/backend/demo/target/*.jar
COPY ${JAR_FILE} PrestaBanco.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "./PrestaBanco.jar"]
