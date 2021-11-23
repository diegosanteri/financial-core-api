# Build Java code
FROM adoptopenjdk/openjdk11:ubi as builder
WORKDIR /workspace
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY pom.xml pom.xml
COPY src src
## test and build
RUN ./mvnw clean test install


##  Use JRE to run jar
FROM adoptopenjdk/openjdk11:ubi-jre
WORKDIR /
COPY --from=builder /workspace/target/financial-core-api.jar financial-core-api.jar
USER 65532:65532
ENTRYPOINT exec java $JAVA_OPTS -jar financial-core-api.jar
