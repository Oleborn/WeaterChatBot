package oleborn.weatherchatbot.weatherapp.feignclient;

import feign.FeignException;
import feign.codec.ErrorDecoder;
import oleborn.weatherchatbot.weatherapp.exception.CityNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Аннотация @Configuration указывает, что класс содержит конфигурацию Spring
@Configuration
public class FeignConfig {

    // Создаёт бин ErrorDecoder для обработки ошибок Feign-клиента
    @Bean
    public ErrorDecoder errorDecoder() {
        // Лямбда-реализация интерфейса ErrorDecoder
        return (methodKey, response) -> {
            // Обработка HTTP 404 ошибки (Not Found)
            if (response.status() == 404) {
                // Возвращаем кастомное исключение для случая "город не найден"
                return new CityNotFoundException();
            }
            // Для всех остальных ошибок возвращаем стандартное Feign исключение
            return FeignException.errorStatus(methodKey, response);
        };
    }
}