package oleborn.weatherchatbot.weatherapp;

import oleborn.weatherchatbot.weatherapp.dto.Weather;
import oleborn.weatherchatbot.weatherapp.dto.WeatherResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class ParserData {


    public String parseWeatherToMessage(String login, WeatherResponse weather) {
        return Stream.of(
                        formatHeader(login),
                        formatLocation(weather),
                        formatWeatherConditions(weather),
                        formatTemperature(weather),
                        formatWind(weather),
                        formatRain(weather),
                        formatVisibility(weather),
                        formatAstroData(weather),
                        formatFooter(weather)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));
    }

    private String formatHeader(String login) {
        return """
            <b>Ответ по запросу</b> %s:
            """.formatted(login);
    }

    private String formatLocation(WeatherResponse weather) {
        return """
            🌍 <b><u>%s</u></b> (%s)
            📍 <b>Координаты:</b> %.4f°N, %.4f°E
            """.formatted(
                weather.name(),
                weather.sys().country(),
                weather.coord().lat(),
                weather.coord().lon()
        );
    }

    private String formatWeatherConditions(WeatherResponse weather) {
        Weather current = weather.weather().getFirst();
        return """
            %s <b>Текущая погода:</b>
            - %s
            """.formatted(
                getWeatherEmoji(current.id()),
                capitalizeFirstLetter(current.description())
        );
    }

    private String formatTemperature(WeatherResponse weather) {
        return """
            🌡 <b>Температура:</b>
            - Реальная: %.1f°C
            - Ощущается как: %.1f°C
            - Влажность: %d%%
            - Давление: %d гПа
            """.formatted(
                weather.main().temp(),
                weather.main().feels_like(),
                weather.main().humidity(),
                weather.main().pressure()
        );
    }

    private String formatWind(WeatherResponse weather) {
        return """
            🌬 <b>Ветер:</b>
            - Направление: %s
            - Скорость: %.1f м/с
            %s""".formatted(
                getWindDirection(weather.wind().deg()),
                weather.wind().speed(),
                weather.wind().gust() > 0
                        ? "- Порывы до: %.1f м/с".formatted(weather.wind().gust())
                        : ""
        );
    }

    private String formatRain(WeatherResponse weather) {
        if (weather.rain() == null) return null;

        return """
            🌧 <b>Осадки:</b>
            - Дождь: %.1f мм
            """.formatted(weather.rain().oneHour());
    }

    private String formatVisibility(WeatherResponse weather) {
        return """
            \n<b>Видимость:</b> %d км
            ☁ <b>Облачность:</b> %d%%
            """.formatted(
                weather.visibility() / 1000,
                weather.clouds().all()
        );
    }

    private String formatAstroData(WeatherResponse weather) {
        return """
            🌞 <b>Солнце:</b>
            - Восход: %s
            - Закат: %s
            """.formatted(
                formatUnixTime(weather.sys().sunrise(), weather.timezone()),
                formatUnixTime(weather.sys().sunset(), weather.timezone())
        );
    }

    private String formatFooter(WeatherResponse weather) {
        return """
            ℹ <b>Актуально на:</b> %s
            """.formatted(
                formatUnixTime(weather.dt(), weather.timezone())
        );
    }

    // Вспомогательные методы
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private String formatUnixTime(long timestamp, int timezoneOffset) {
        Instant instant = Instant.ofEpochSecond(timestamp + timezoneOffset);
        return instant.atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    // Определение направления ветра
    private String getWindDirection(int degrees) {
        String[] directions = {"Северный", "Северо-Восточный", "Восточный", "Юго-Восточный", "Южный", "Юго-Западный", "Западный", "Северо-Западный"};
        return directions[(int) Math.round(((degrees % 360) / 45.0)) % 8];
    }

    // Подбор эмодзи для погоды
    private String getWeatherEmoji(int weatherId) {
        return switch (weatherId / 100) {
            case 2 -> "⛈";
            case 3, 5 -> "🌧";
            case 6 -> "❄️";
            case 7 -> "🌫";
            case 8 -> weatherId == 800 ? "☀️" : "☁️";
            default -> "🌤";
        };
    }
}
