package edu.java.bot.configuration.retry;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.util.retry.RetryBackoffSpec;

@Data
@Configuration
@RequiredArgsConstructor
public class RetryProperties {
    @Value("${retry.status-code}")
    private final List<HttpStatus> statusCode;

    private final RetryBackoffSpec retry;
}
