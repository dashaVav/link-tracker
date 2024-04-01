package edu.java.configuration.retry;

import edu.java.exception.custom.ResourceUnavailableException;
import java.time.Duration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Data
@Configuration
@ConditionalOnProperty(prefix = "retry", name = "strategy", havingValue = "const")
public class ConstantRetry {

    @Value("${retry.max-attempts}")
    private int maxAttempts;

    @Value("${retry.delay}")
    private long delay;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {

        return Retry.fixedDelay(maxAttempts, Duration.ofMillis(delay))
            .filter(throwable -> throwable instanceof ResourceUnavailableException
                || throwable instanceof WebClientRequestException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                throw new ResourceUnavailableException("Try later", HttpStatus.SERVICE_UNAVAILABLE);
            });
    }
}

