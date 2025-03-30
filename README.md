# WeatherChatBot

Телеграм-бот на Spring Boot для получения информации о погоде через OpenWeatherMap API.

## Описание проекта

WeatherChatBot - это обучающий проект, демонстрирующий создание Telegram-бота с использованием Spring Boot. Бот позволяет пользователям получать актуальную информацию о погоде в различных городах через интеграцию с OpenWeatherMap API.

## Технологии

- Java 21
- Spring Boot 3.4.4
- Spring Cloud OpenFeign
- Telegram Bot API (telegrambots 6.9.7.1)
- Lombok
- Caffeine Cache

## Архитектура проекта

Проект имеет модульную структуру и следует принципам чистой архитектуры:

### Основные компоненты

- **bot** - содержит классы для работы с Telegram Bot API
  - `Bot.java` - основной класс бота, наследующий TelegramLongPollingBot
  - `BotConfig.java` - конфигурация и регистрация бота

- **weatherapp** - содержит компоненты для работы с погодным API
  - **feignclient** - интеграция с OpenWeatherMap API
    - `OpenWeatherMapClient.java` - Feign-клиент для запросов к API
  - **dto** - объекты передачи данных
  - **service** - бизнес-логика
  - **cache** - кэширование запросов
  - **exception** - обработка исключений
  - **requestlimiter** - ограничение количества запросов

- **output** - форматирование вывода

- **updatehandler** - обработка входящих сообщений от пользователей

## Функциональность

- Получение текущей погоды по названию города
- Кэширование результатов для оптимизации запросов
- Ограничение количества запросов к API
- Обработка различных команд пользователя

## Настройка и запуск

### Предварительные требования

- JDK 21
- Maven
- Telegram Bot Token (получается через [@BotFather](https://t.me/BotFather))
- API ключ от [OpenWeatherMap](https://openweathermap.org/api)

### Настройка

1. Клонировать репозиторий:
   ```bash
   git clone https://github.com/Oleborn/WeaterChatBot.git
   cd WeaterChatBot
   ```

2. Настроить параметры в `application.properties`:
   ```properties
   # Токен бота Telegram
   bot.name-bot=YOUR_BOT_NAME
   bot.bot-token=YOUR_BOT_TOKEN
   
   # API ключ OpenWeatherMap
   openweather.base-url=https://api.openweathermap.org
   openweather.api-key=YOUR_API_KEY
   ```

### Сборка и запуск

```bash
mvn clean package
java -jar target/WeatherChatBot-0.0.1-SNAPSHOT.jar
```

## Использование

После запуска бота вы можете взаимодействовать с ним в Telegram:

1. Найдите своего бота по имени в Telegram
2. Отправьте название города через команду "/погода", чтобы получить информацию о погоде в конкретном городе
3. Используйте команду `/help` для получения списка доступных команд

## Примеры команд

- `/start` - начать взаимодействие с ботом
- `/help` - получить справку
- `/погода Москва` - получить погоду в Москве
- `/погода Лондон` - получить погоду в Лондоне

## Лицензия

Проект распространяется под открытой лицензией.

## Автор

- [Oleborn](https://github.com/Oleborn)
