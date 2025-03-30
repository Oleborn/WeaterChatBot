package oleborn.weatherchatbot.weatherapp.dto;

public record Weather(
        /** Идентификатор погодных условий */
        int id,

        /** Группа параметров погоды (Rain, Snow, Extreme и т.д.) */
        String main,

        /** Текстовое описание погоды на указанном языке */
        String description,

        /** Иконка погоды (см. https://openweathermap.org/weather-conditions) */
        String icon
    ) {}