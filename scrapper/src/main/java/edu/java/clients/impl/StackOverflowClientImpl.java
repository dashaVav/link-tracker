package edu.java.clients.impl;

import edu.java.clients.StackOverflowClient;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClientImpl implements StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public StackOverflowDTO fetchQuestion(Long id) {
        return webClient.get()
            .uri("/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
            .retrieve()
            .bodyToMono(StackOverflowDTO.class)
            .block();
    }
}
