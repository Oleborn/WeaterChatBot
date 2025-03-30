package oleborn.weatherchatbot.updatehandler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateHandlerFactory {

    private final List<UpdateHandler> handlers;

    public Optional<UpdateHandler> getHandler(Update update) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst();
    }
}