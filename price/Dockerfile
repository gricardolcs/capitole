FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/price-app.jar app.jar
EXPOSE 8080
USER 1000
ENTRYPOINT ["java", "-jar", "app.jar"]