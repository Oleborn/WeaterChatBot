package oleborn.weatherchatbot.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Rain(
        /** Объем осадков за последний час (мм) */
        @JsonProperty("1h") double oneHour
    ) {}