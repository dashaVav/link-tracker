package edu.java.client.impl;

import edu.java.client.StackOverflowClient;
import edu.java.configuration.retry.RetryProperties;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import edu.java.exception.custom.ResourceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;
    private final RetryProperties retryProperties;

    public StackOverflowClientImpl(String baseUrl, RetryProperties retryProperties) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryProperties = retryProperties;
    }

    @Override
    public StackOverflowDTO fetchAnswersByQuestionId(Long id) {
        return webClient.get()
            .uri("/questions/{question}/answers?order=desc&site=stackoverflow", id)
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException(
                    "Try later",
                    (HttpStatus) response.statusCode()
                ))
            )
            .bodyToMono(StackOverflowDTO.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public String getMessage(StackOverflowDTO.Item event) {
        return String.format("%s добавил(а) новый ответ на вопрос", event.owner().displayName());
    }
}
