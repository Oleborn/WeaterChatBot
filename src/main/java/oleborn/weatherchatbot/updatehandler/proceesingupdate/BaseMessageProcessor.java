package oleborn.weatherchatbot.updatehandler.proceesingupdate;

import lombok.RequiredArgsConstructor;
import oleborn.weatherchatbot.updatehandler.message.MessageProcessor;
import oleborn.weatherchatbot.weatherapp.service.WeatherService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class BaseMessageProcessor implements MessageProcessor {

    private final WeatherService weatherService;

    @Override
    public void process(Update update) {
        weatherService.getWeather(update);
    }
}