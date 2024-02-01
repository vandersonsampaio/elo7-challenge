FROM openjdk:17-alpine
RUN mkdir /apps
COPY target/sonda.candidato-*.jar /apps/planet-probe.jar
ENTRYPOINT ["java", "-jar", "/apps/planet-probe.jar"]