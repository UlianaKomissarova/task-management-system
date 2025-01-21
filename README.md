# Task Management System

Это простая система управления задачами с возможностью создания, редактирования, 
удаления и просмотра задач. Сервис поддерживает аутентификацию и авторизацию пользователей по email и паролю.
Доступ к методам API зависит от роли пользователя (ADMIN, USER).

Документация доступна по ссылке: http://localhost:8080/swagger-ui/index.html

---

## Стек:

- **Java 21**
- **Spring Boot**
- **PostgreSQL 16**
- **Liquibase**
- **Spring Security**
- **Spring Data**
- **Gradle**
- **JUnit, Mockito**
- **Логирование Slf4J через АОП**
- **Глобальный обработчик ошибок**
- **Docker**
- **Swagger OpenAPI**

---

## Установка и локальный запуск
Склонируйте репозиторий на свой локальный компьютер:

```bash
git clone git@github.com:UlianaKomissarova/task-management-system.git
cd task-management-system
```

Запустите проект с помощью команды:

```bash
docker-compose up --build
```
