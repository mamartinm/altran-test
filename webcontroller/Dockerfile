FROM adoptopenjdk/openjdk11:jre
VOLUME /tmp
ARG JAR_FILE

ENV _JAVA_OPTIONS "-Xms256m -Xmx512m -Djava.awt.headless=true"

RUN mkdir /opt/app
COPY ${JAR_FILE} /opt/app/webcontroller.jar
CMD ["java", "-jar", "/opt/app/webcontroller.jar"]