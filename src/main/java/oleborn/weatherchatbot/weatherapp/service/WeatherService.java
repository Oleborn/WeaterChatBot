package oleborn.weatherchatbot.weatherapp.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface WeatherService {

    void getWeather(Update update);

}
