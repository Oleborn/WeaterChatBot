package oleborn.weatherchatbot.weatherapp.dto;

public record Sys(
        /** Код страны (RU, US и т.д.) */
        String country,

        /** Время восхода солнца (Unix timestamp) */
        long sunrise,

        /** Время заката солнца (Unix timestamp) */
        long sunset
    ) {}