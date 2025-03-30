package oleborn.weatherchatbot.weatherapp.dto;

public record Coord(
        /** Долгота */
        double lon,

        /** Широта */
        double lat
    ) {}