package oleborn.weatherchatbot.weatherapp.dto;

public record Main(
        /** Температура (по умолчанию в Кельвинах) */
        double temp,

        /** Ощущаемая температура */
        double feels_like,

        /** Минимальная текущая температура */
        double temp_min,

        /** Максимальная текущая температура */
        double temp_max,

        /** Атмосферное давление (в гПа) */
        int pressure,

        /** Влажность (в %) */
        int humidity,

        /** Давление на уровне моря (в гПа) */
        int sea_level,

        /** Давление на уровне земли (в гПа) */
        int grnd_level
    ) {}