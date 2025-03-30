package oleborn.weatherchatbot.weatherapp.dto;

public record Wind(
        /** Скорость ветра (м/с) */
        double speed,

        /** Направление ветра (градусы, метеорологические) */
        int deg,

        /** Порывы ветра (м/с) */
        double gust
    ) {}