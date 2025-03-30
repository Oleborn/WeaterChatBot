package oleborn.weatherchatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource("file:.properties")
@EnableCaching
@EnableFeignClients
@EnableScheduling
public class WeatherChatBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherChatBotApplication.class, args);
    }

}
