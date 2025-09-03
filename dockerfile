# Используем официальный образ OpenJDK
FROM openjdk:21-jdk-alpine

# Указываем рабочую директорию
WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]