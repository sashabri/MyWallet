FROM eclipse-temurin:17

EXPOSE 8080

COPY target/MyWallet-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-Dspring.datasource.url=${DB_URL}" , "-jar", "app.jar"]