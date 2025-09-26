# LinkCompresor

> Сервис для сокращения длинных ссылок.
> Быстро. Просто. Надёжно.

## Оглавление
- [Описание](#описание)
- [Функционал](#функционал)
- [Технологии](#технологии)
- [Развёртывание](#развёртывание)
- [Использование](#использование)
- [API](#api)
- [Примеры запросов](#примеры-запросов)
- [CI/CD и инфраструктура](#cicd-и-инфраструктура)


## Описание

LinkCompresor — это сервис для сокращения длинных URL-адресов. 

## Функционал
- Сокращение длинных ссылок
- Перенаправление по коротким ссылкам
- REST API для интеграции
- Валидация ссылок

## Технологии
- Java 21
- Spring Boot
- Spring Data JPA
- Redis (для хранения данных)
- Maven
- Lombok
- Docker, Docker Compose
- Nginx (reverse proxy)
- Jenkins (CI/CD)

## Развёртывание

### 1. Клонирование репозитория
```bash
git clone https://github.com/bag234/LinkCompresor.git
cd LinkCompresor
```

### 2. Сборка приложения
```bash
./mvnw clean package -U -DskipTests
```

### 3. Запуск через Docker Compose

Перед запуском убедитесь, что установлен Docker и Docker Compose.

```bash
docker compose up -d --build
```

- Приложение будет доступно на http://localhost:8884
- Redis поднимается автоматически

### 4. Nginx (reverse proxy, https)

В продакшене используется Nginx для проксирования и SSL. Пример конфига — в файле `nginx.conf`.

- HTTP перенаправляет на HTTPS
- HTTPS проксирует на backend (порт 8884)
- Для сертификатов используется Let's Encrypt

### 5. CI/CD (Jenkins)

В проекте есть Jenkinsfile для автоматической сборки, публикации Docker-образа и деплоя на сервер через SSH.

- Сборка и публикация Docker-образа
- Деплой через docker compose
- Обновление конфигурации Nginx

## Использование

- Перейдите на главную страницу (`/`) для сокращения ссылок через веб-интерфейс
- Используйте API для интеграции с другими сервисами

### Основные эндпоинты API
- `POST /api/links` — создать короткую ссылку
- `GET /{shortUrl}` — перенаправление по короткой ссылке
- `GET /api/links/{shortUrl}/stats` — статистика по ссылке

## Примеры запросов
***!!! Не завершено документирования !!!***
**Сократить ссылку:**
```http
POST /api/links
Content-Type: application/json

{
  "originalUrl": "https://example.com/very/long/url"
}
```
**Ответ:**
```json
{
  "shortUrl": "http://localhost:8884/abc123"
}
```


## CI/CD и инфраструктура

- **Dockerfile** — сборка приложения в контейнере
- **docker-compose.yml** — запуск приложения и Redis
- **nginx.conf** — проксирование и SSL
- **Jenkinsfile** — автоматизация сборки и деплоя

### Быстрый старт для разработки

1. Соберите jar-файл:
    ```bash
    ./mvnw clean package -U -DskipTests
    ```
2. Запустите через Docker Compose:
    ```bash
    docker compose up -d --build
    ```
3. Приложение будет доступно на http://localhost:8884

### Переменные окружения (docker-compose.yml)
- `APP_DOMAIN` — домен приложения
- `APP_DATA_MAIN` — строка подключения к Redis (основное хранилище)
- `APP_DATA_ALIAS` — строка подключения к Redis (алиасы)
- `SPRING_BOOT_ADMIN_CLIENT_URL` — URL Spring Boot Admin
- `APP_HTTPS` — использовать ли HTTPS
