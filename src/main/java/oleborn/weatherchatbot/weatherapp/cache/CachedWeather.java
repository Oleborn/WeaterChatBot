package oleborn.weatherchatbot.weatherapp.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oleborn.weatherchatbot.output.OutputsMethods;
import oleborn.weatherchatbot.weatherapp.dto.WeatherResponse;
import oleborn.weatherchatbot.weatherapp.feignclient.OpenWeatherMapClient;
import oleborn.weatherchatbot.weatherapp.requestlimiter.GlobalRateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

// Аннотация @Component указывает, что класс является Spring-компонентом
// @RequiredArgsConstructor генерирует конструктор для final полей
// @Slf4j добавляет логгер
@Component
@RequiredArgsConstructor
@Slf4j
public class CachedWeather {

    // API-ключ для OpenWeatherMap (инжектится из конфигурации)
    @Value("${openweather.api-key}")
    private String apiKey;

    // Клиент для работы с API OpenWeatherMap
    private final OpenWeatherMapClient openWeatherMapClient;
    // Лимитер запросов
    private final GlobalRateLimiter globalRateLimiter;
    // Сервис для отправки сообщений
    private final OutputsMethods outputsMethods;

    // Метод с кэшированием результатов (weatherCache - имя кэша, key - город)
    @Cacheable(value = "weatherCache", key = "#city")
    public WeatherResponse getCachedWeather(String city, long chatId, String login) {
        // Логируется только при реальном запросе к API (не при взятии из кэша)
        log.info("Выполняется реальный запрос к API для города: {}", city);

        // Проверка лимита запросов
        if (!globalRateLimiter.allowRequest()){
            outputsMethods.outputMessage(chatId, "Слишком много запросов. Попробуйте позже.");
            return null;
        }

        // Запрос погоды (единицы измерения - метрические, язык - русский)
        WeatherResponse response = openWeatherMapClient.getWeather(city, apiKey, "metric", "ru");

        // Логирование полученных данных
        log.info("Получены данные от openWeather для города {}: {}", city, response);

        return response;
    }
}
