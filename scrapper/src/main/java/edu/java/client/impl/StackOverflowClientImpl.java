package edu.java.client.impl;

import edu.java.client.StackOverflowClient;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public StackOverflowDTO fetchAnswersByQuestionId(Long id) {
        return webClient.get()
            .uri("/questions/{question}/answers?order=desc&site=stackoverflow", id)
            .retrieve()
            .bodyToMono(StackOverflowDTO.class)
            .block();
    }

    @Override
    public String getMessage(StackOverflowDTO.Item event) {
        return String.format("%s добавил(а) новый ответ на вопрос", event.owner().displayName());
    }
}
