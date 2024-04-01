package edu.java.bot.configuration.retry;

import edu.java.bot.exception.api.ResourceUnavailableException;
import java.time.Duration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Data
@Configuration
@ConditionalOnProperty(prefix = "retry", name = "strategy", havingValue = "exp")
public class ExpRetry {
    @Value("${retry.max-attempts}")
    private int maxAttempts;

    @Value("${retry.delay}")
    private long delay;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {
        return Retry.backoff(maxAttempts, Duration.ofMillis(delay))
            .filter(throwable -> throwable instanceof ResourceUnavailableException
                || throwable instanceof WebClientRequestException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                throw new ResourceUnavailableException();
            });
    }
}
