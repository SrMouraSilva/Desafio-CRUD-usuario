FROM gcr.io/distroless/java:8

USER nonroot:nonroot

COPY --chown=nonroot:nonroot build/libs/desafio-0.0.1-SNAPSHOT.jar /microservice.jar

EXPOSE 8080

ENV _JAVA_OPTIONS "-XX:MinRAMPercentage=60.0 -XX:MaxRAMPercentage=90.0 \
    -Djava.security.egd=file:/dev/./urandom \
    -Djava.awt.headless=true -Dfile.encoding=UTF-8 \
    -Dspring.output.ansi.enabled=ALWAYS \
    -Dspring.profiles.active=default"

ENTRYPOINT ["java", "-jar", "/microservice.jar"]