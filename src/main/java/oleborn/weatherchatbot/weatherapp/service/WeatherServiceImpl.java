package oleborn.weatherchatbot.weatherapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oleborn.weatherchatbot.output.OutputsMethods;
import oleborn.weatherchatbot.weatherapp.ParserData;
import oleborn.weatherchatbot.weatherapp.cache.CachedWeather;
import oleborn.weatherchatbot.weatherapp.dto.WeatherResponse;
import oleborn.weatherchatbot.weatherapp.exception.CityNotFoundException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


// Сервисный класс для работы с погодой
@Service
@RequiredArgsConstructor // Автоматически создает конструктор с final полями
@Slf4j // Добавляет логгер
public class WeatherServiceImpl implements WeatherService {

    // Зависимости сервиса
    private final OutputsMethods outputsMethods; // Для отправки сообщений
    private final ParserData parserData; // Для парсинга данных о погоде
    private final CachedWeather cachedWeather; // Для получения кэшированных данных о погоде

    // Основной метод обработки запроса погоды
    @Override
    public void getWeather(Update update) {
        // Проверка, что сообщение содержит команду /погода
        if (!update.getMessage().getText().startsWith("/погода")) {
            return;
        }

        // Извлечение данных из сообщения
        Message message = update.getMessage();
        String login = message.getFrom().getUserName(); // Логин пользователя
        long chatId = message.getChatId(); // ID чата
        String text = message.getText(); // Текст сообщения
        Integer messageId = message.getMessageId(); // ID сообщения

        // Извлечение названия города
        String city = extractCity(text);
        if (city.isEmpty()) {
            sendCityNotSpecifiedMessage(chatId, login);
            return;
        }

        // Обработка запроса погоды
        processWeatherRequest(city, chatId, login);
        // Попытка удалить исходное сообщение
        tryDeleteOriginalMessage(chatId, messageId, text);
    }

    // Извлекает название города из команды
    private String extractCity(String commandText) {
        return commandText.replace("/погода", "").trim();
    }

    // Отправляет сообщение об отсутствии города
    private void sendCityNotSpecifiedMessage(long chatId, String login) {
        outputsMethods.outputMessage(chatId, login + " укажи город в формате: /погода Москва");
    }

    // Обрабатывает запрос погоды для указанного города
    private void processWeatherRequest(String city, long chatId, String login) {
        try {
            // Получаем данные о погоде (из кэша или API)
            WeatherResponse weatherInfo = cachedWeather.getCachedWeather(city, chatId, login);
            if (weatherInfo == null) {
                return;
            }
            // Парсим данные и отправляем сообщение
            outputsMethods.outputMessage(chatId, parserData.parseWeatherToMessage(login, weatherInfo));
        } catch (CityNotFoundException e) {
            // Обработка случая, когда город не найден
            outputsMethods.outputMessage(chatId, login + " указанный тобой город " + city + " не найден. Подумай еще.");
        }
    }

    // Пытается удалить исходное сообщение с командой
    private void tryDeleteOriginalMessage(long chatId, Integer messageId, String originalText) {
        try {
            outputsMethods.execute(new DeleteMessage(String.valueOf(chatId), messageId));
            log.debug("Сообщение удалено: {}", originalText);
        } catch (TelegramApiException e) {
            log.warn("Не удалось удалить сообщение. Чат: {}, Сообщение: {}. Причина: {}",
                    chatId, messageId, e.getMessage());
        }
    }
}
