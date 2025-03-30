package oleborn.weatherchatbot.weatherapp.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

// Аннотация @Configuration указывает, что класс содержит конфигурацию Spring
// @EnableCaching включает поддержку кэширования в приложении
@Configuration
@EnableCaching
public class CacheConfig {

    // Создаёт бин с настройками Caffeine (библиотека кэширования)
    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .maximumSize(60)               // Максимальное количество записей в кэше
                .expireAfterWrite(5, TimeUnit.MINUTES);  // Время жизни записи после создания
    }

    // Создаёт менеджер кэша на основе Caffeine
    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("weatherCache");  // Создаём менеджер с именем кэша
        cacheManager.setCaffeine(caffeine);    // Применяем настройки Caffeine
        return cacheManager;
    }
}