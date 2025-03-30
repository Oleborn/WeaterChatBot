package oleborn.weatherchatbot.weatherapp.feignclient;

import oleborn.weatherchatbot.weatherapp.dto.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Аннотация @FeignClient указывает, что это Feign-клиент для REST API
// name - имя клиента для создания бина, url - базовый адрес из конфига
@FeignClient(name = "openweathermap", url = "${openweather.base-url}")
public interface OpenWeatherMapClient {

    // GET-запрос к эндпоинту /weather
    @GetMapping("/weather")
    WeatherResponse getWeather(
            @RequestParam("q") String city,         // Параметр запроса - город
            @RequestParam("appid") String apiKey,   // Параметр запроса - API-ключ
            @RequestParam("units") String units,    // Параметр запроса - единицы измерения
            @RequestParam("lang") String lang       // Параметр запроса - язык ответа
    );
}