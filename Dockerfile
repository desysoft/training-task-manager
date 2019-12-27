FROM adoptopenjdk/openjdk11-openj9:jre-11.0.3_7_openj9-0.14.0-alpine
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV AB_ENABLED=jmx_exporter
COPY target/lib/* /opt/deployments/lib/
COPY target/*-runner.jar /opt/deployments/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","-Dquarkus.http.port=8081","-Djava.net.preferIPv4Stack=true","-Djava.net.preferIPv4Addresses","/opt/deployments/app.jar","-Xmx512m"]