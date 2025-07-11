# Trains

Данный проект представляет собой серверную часть клиент-серверного приложения для бронирования билетов в поезде. В приложении предусмотрена работа в режиме незарегистрированного пользователя (просмотр расписания, просмотр схем вагонов поезда),
работа в режиме зарегистрированного пользователя — клиента (бронирование билетов, просмотр и редактирование данных в личном кабинете) и работа в режиме администратора (составление расписания рейсов, добавление и изменение новых поездов).

Клиентская часть приложения находится в репозитории: https://github.com/ArtyomKrasyuk/Trains-Frontend

# Технологии
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Lombok
- Liquibase
- PostgreSQL
- Redis
- Docker

# Реализация работы с базой данных 

В проекте было использовано две базы данных: PostgreSQL и Redis. PostgreSQL используется для хранения всех данных о рейсах, поездах и пользователях, а Redis используется для хранения сессии авторизации пользователя.

Взаимодействия с PostgreSQL осуществляется с помощью Spring Data JPA, для отслеживания изменения схемы базы данных в проекте используется Liquibase. PostgreSQL и Redis запускаются в контейнерах Docker.

# Система авторизации

В системе используется авторизация с помощью http-сессии, которую создаёт Spring Security. В данном приложении для авторизованного пользователя предусмотрена роль CLIENT, для администратора — ADMIN, а запросы от пользователей, не обладающих
данными ролями, рассматриваются как запросы от незарегистрированного пользователя.

# Пример работы приложения с интерфейсом

Форма авторизации:
![Снимок экрана от 2025-05-28 19-20-18](https://github.com/user-attachments/assets/2ff44059-9279-4e8b-b8fd-7823064d07d9)

Главная страница:
![_home_artyom_Data_web_trains_index html](https://github.com/user-attachments/assets/9985d516-48bc-4033-9261-e341dd59e276)

Страница схемы вагонов:
![_home_artyom_Data_web_trains_selection_of_seats html_tripId=505 (копия)](https://github.com/user-attachments/assets/198c4c02-ed7f-4d0b-aac4-f0d8675c046c)

Страница подтверждения данных бронирования:
![_home_artyom_Data_web_trains_booking-confirmation html_tripId=652](https://github.com/user-attachments/assets/66f7e81f-7d24-462d-b854-c9c70f0c47de)

Личный кабинет:
![_home_artyom_Data_web_trains_personal_account html](https://github.com/user-attachments/assets/7c051365-d026-42ec-ae87-021d343cb1e5)



