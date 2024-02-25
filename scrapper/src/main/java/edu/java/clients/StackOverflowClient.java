package edu.java.clients;

import edu.java.dto.stackoverflow.StackOverflowDTO;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClient(String baseUrl) {
        String baseUrlDefault = "https://api.stackexchange.com/2.3";
        if (!baseUrl.isEmpty()) {
            baseUrlDefault = baseUrl;
        }

        webClient = WebClient.builder()
            .baseUrl(baseUrlDefault)
            .build();
    }

    public StackOverflowDTO fetchQuestion(Long id) {
        return webClient.get()
            .uri("/questions/{id}?order=desc&sort=activity&site=stackoverflow", id)
            .retrieve()
            .bodyToMono(StackOverflowDTO.class)
            .block();
    }
}
