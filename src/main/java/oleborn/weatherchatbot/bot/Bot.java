package oleborn.weatherchatbot.bot;

import jakarta.annotation.Resource;
import oleborn.weatherchatbot.updatehandler.UpdateHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

// Аннотация @Configuration указывает, что класс содержит конфигурацию Spring
@Configuration
// Класс реализует TelegramLongPollingBot, что делает его ботом для Telegram с Long Polling
public class Bot extends TelegramLongPollingBot {

    // Аннотация @Value внедряет значение из properties-файла (например, application.yml)
    // В данном случае - имя бота из конфигурации
    @Value("${bot.name-bot}")
    private String nameBot;

    // Внедряет токен бота из конфигурации
    @Value("${bot.bot-token}")
    private String botToken;

    // Аннотация @Resource внедряет зависимость (аналог @Autowired, но стандартный Java)
    // @Lazy означает, что бин будет создан только при первом обращении (ленивая инициализация)
    @Resource
    @Lazy
    private UpdateHandlerFactory updateHandlerFactory;

    // Реализация метода из TelegramLongPollingBot, возвращает имя бота
    @Override
    public String getBotUsername() {
        return nameBot;
    }

    // Реализация метода из TelegramLongPollingBot, возвращает токен бота
    @Override
    public String getBotToken() {
        return botToken;
    }

    // Обработчик входящих обновлений (сообщений, команд и т. д.)
    @Override
    public void onUpdateReceived(Update update) {
        // Получаем обработчик из фабрики и, если он существует, вызываем handle()
        updateHandlerFactory.getHandler(update).ifPresent(handler -> handler.handle(update));
    }
}
