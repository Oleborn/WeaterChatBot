package oleborn.weatherchatbot.weatherapp.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException() {
        super("Город не найден");
    }
}