package oleborn.weatherchatbot.weatherapp.dto;

import java.util.List;

public record WeatherResponse(
        /** Географические координаты (широта, долгота) */
        Coord coord,

        /** Массив погодных условий (обычно 1 элемент) */
        List<Weather> weather,

        /** Внутренний параметр (обычно "stations") */
        String base,

        /** Основные метеорологические данные */
        Main main,

        /** Видимость в метрах (максимум 10км) */
        int visibility,

        /** Данные о ветре */
        Wind wind,

        /** Данные об осадках (дождь) */
        Rain rain,

        /** Облачность */
        Clouds clouds,

        /** Время расчета данных (Unix timestamp) */
        long dt,

        /** Системная информация */
        Sys sys,

        /** Сдвиг от UTC в секундах */
        int timezone,

        /** ID города в OpenWeatherMap */
        long id,

        /** Название города */
        String name,

        /** Внутренний код ответа (200 = успех) */
        int cod
) {}