FROM gradle:8.14.2-jdk21-alpine AS builder
WORKDIR /app
COPY gradlew gradlew.bat build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN ./gradlew --no-daemon dependencies
COPY src ./src
RUN ./gradlew --no-daemon clean build -x test -x check && rm -rf ~/.gradle/caches

FROM eclipse-temurin:21-jre-alpine-3.20 AS runtime
WORKDIR /app
ARG APP_VERSION
LABEL org.opencontainers.image.title="DeplifyBot" \
      org.opencontainers.image.description="Bot delivers Telegram notifications for Github and Dockerhub events." \
      org.opencontainers.image.authors="s.roman3455" \
      org.opencontainers.image.source="https://github.com/Roman3455/DeplifyBot" \
      org.opencontainers.image.licenses="Apache-2.0" \
      org.opencontainers.image.version=${APP_VERSION}
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
COPY --from=builder --chown=appuser:appgroup /app/build/libs/app-*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"
ARG SPRING_PROFILE=prod
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILE}
ARG PORT=8080
ENV SERVER_PORT=${PORT}
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
