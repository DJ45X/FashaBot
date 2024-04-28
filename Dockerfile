FROM amazoncorretto:22.0.0-alpine AS builder

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN mvn clean package

FROM amazoncorretto:22.0.0-alpine AS final

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]