package oleborn.weatherchatbot.output;

import oleborn.weatherchatbot.bot.Bot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Аннотация @Component указывает, что класс является Spring-компонентом
@Component
// Класс наследуется от Bot, что дает доступ к методам TelegramLongPollingBot
public class OutputsMethods extends Bot {

    // Метод для отправки сообщения пользователю
    // @param id - идентификатор чата
    // @param text - текст сообщения
    public void outputMessage(Long id, String text) {
        // Создание объекта сообщения с помощью билдера
        SendMessage ms = SendMessage.builder()
                .chatId(id)          // Установка ID чата
                .parseMode("HTML")    // Установка режима разметки HTML
                .text(text)           // Установка текста сообщения
                .build();
        try {
            // Отправка сообщения через API Telegram
            sendApiMethod(ms);
        } catch (TelegramApiException e) {
            // Обработка ошибки отправки сообщения
            throw new RuntimeException(e);
        }
    }
}
