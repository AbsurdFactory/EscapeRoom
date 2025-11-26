# syntax=docker/dockerfile:1

################################################################################
FROM eclipse-temurin:21-jdk-jammy AS deps

WORKDIR /build

RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

COPY pom.xml pom.xml

RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -DskipTests

################################################################################
FROM deps AS package

WORKDIR /build

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests && \
    mv target/$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
FROM eclipse-temurin:21-jre-jammy AS final

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

WORKDIR /app

COPY --from=package /build/target/app.jar app.jar

EXPOSE 3306

ENTRYPOINT ["java", "-jar", "app.jar"]
