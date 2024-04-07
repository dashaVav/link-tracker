package edu.java.client.impl;

import edu.java.client.BotClient;
import edu.java.configuration.retry.RetryProperties;
import edu.java.dto.api.response.ApiErrorResponse;
import edu.java.dto.bot.LinkUpdateResponse;
import edu.java.exception.api.ApiBadRequestException;
import edu.java.exception.custom.ResourceUnavailableException;
import edu.java.service.scheduler.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClientImpl implements BotClient, NotificationService {
    private final WebClient webClient;
    private final RetryProperties retryProperties;

    public BotClientImpl(String baseUrl, RetryProperties retryProperties) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryProperties = retryProperties;
    }

    @Override
    public void sendUpdate(LinkUpdateResponse linkUpdateRequest) {
        webClient.post()
            .uri("/updates")
            .body(BodyInserters.fromValue(linkUpdateRequest))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException(
                    "Try later",
                    (HttpStatus) response.statusCode()
                ))
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(Void.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public void sendNotification(LinkUpdateResponse update) {
        sendUpdate(update);
    }
}
