package oleborn.weatherchatbot.updatehandler.channelpost;

import lombok.RequiredArgsConstructor;
import oleborn.weatherchatbot.updatehandler.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class ChannelPostHandler implements UpdateHandler {

    private final ChannelPostProcessor channelPostProcessor;

    @Override
    public boolean canHandle(Update update) {
        return update.hasChannelPost();
    }

    @Override
    public void handle(Update update) {
        channelPostProcessor.process(update.getChannelPost());
    }
}