package oleborn.weatherchatbot.weatherapp.requestlimiter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

// Аннотация @Service указывает, что класс является Spring-сервисом
// @Slf4j добавляет логгер через Lombok
@Service
@Slf4j
public class GlobalRateLimiter {

    // Атомарный счетчик для thread-safe подсчета запросов
    private final AtomicInteger globalRequestCount = new AtomicInteger(0);

    // Сбрасывает счетчик каждую минуту (60,000 мс)
    @Scheduled(fixedRate = 60_000)
    public void resetCounter() {
        log.info("Произведен сброс количества запросов в минуту");
        globalRequestCount.set(0); // Атомарный сброс
    }

    // Проверяет, разрешен ли новый запрос
    public boolean allowRequest() {
        int currentCount = globalRequestCount.incrementAndGet(); // Атомарное увеличение
        log.info("Количество запросов к API за текущую минуту: {}", currentCount);
        return currentCount <= 60; // Лимит 60 запросов в минуту
    }
}