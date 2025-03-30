package oleborn.weatherchatbot.updatehandler.message;

import lombok.RequiredArgsConstructor;
import oleborn.weatherchatbot.updatehandler.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MessageHandler implements UpdateHandler {

    private final MessageProcessor messageProcessor;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage();
    }

    @Override
    public void handle(Update update) {
        messageProcessor.process(update);
    }
}