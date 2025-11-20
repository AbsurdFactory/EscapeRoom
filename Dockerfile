# syntax=docker/dockerfile:1

################################################################################
# Etapa para resolver y descargar dependencias
FROM eclipse-temurin:21-jdk-jammy AS deps

WORKDIR /build

# Instalar Maven dentro del contenedor
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiar pom.xml
COPY pom.xml pom.xml

# Descargar dependencias (aprovechando la cache)
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -DskipTests

################################################################################
# Etapa de build
FROM deps AS package

WORKDIR /build

COPY src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn package -DskipTests && \
    mv target/$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar

################################################################################
# Etapa final para ejecutar la aplicación
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

# Copiar jar generado
COPY --from=package /build/target/app.jar app.jar

EXPOSE 3306

ENTRYPOINT ["java", "-jar", "app.jar"]
