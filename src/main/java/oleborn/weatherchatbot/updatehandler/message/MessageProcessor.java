package oleborn.weatherchatbot.updatehandler.message;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageProcessor {
    void process(Update update);
}