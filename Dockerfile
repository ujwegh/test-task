FROM openjdk:11.0.7-jdk

ADD target/test-task-0.0.1-SNAPSHOT.jar test-task.jar

ENTRYPOINT ["java", "-jar", "/test-task.jar"]