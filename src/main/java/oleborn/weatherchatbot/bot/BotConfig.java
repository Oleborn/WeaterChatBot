package oleborn.weatherchatbot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

// Аннотация @Configuration указывает, что класс содержит конфигурацию Spring
@Configuration
@RequiredArgsConstructor
public class BotConfig {

    private final Bot bot;

    // Создаёт бин TelegramBotsApi, который регистрирует и запускает бота
    @Bean
    public TelegramBotsApi startBot() {
        try {
            // Создаёт сессию бота с настройками по умолчанию
            DefaultBotSession botSession = new DefaultBotSession();

            // Устанавливает параметры бота (например, максимальное количество потоков)
            DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
            defaultBotOptions.setMaxThreads(10);  // Ограничение на 10 потоков

            // Применяет настройки к сессии
            botSession.setOptions(defaultBotOptions);

            // Создаёт API для работы с ботами Telegram
            TelegramBotsApi botsApi = new TelegramBotsApi(botSession.getClass());

            // Регистрирует бота в Telegram API
            botsApi.registerBot(bot);

            return botsApi;
        } catch (TelegramApiException e) {
            // В случае ошибки выбрасывает исключение с пояснением
            throw new RuntimeException("Ошибка запуска бота. Ищи в классе BotConfig", e);
        }
    }
}