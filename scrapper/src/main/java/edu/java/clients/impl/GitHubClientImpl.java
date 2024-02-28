package edu.java.clients.impl;

import edu.java.clients.GitHubClient;
import edu.java.dto.github.GitHubDTO;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GitHubDTO fetchRepo(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(GitHubDTO.class)
            .block();
    }
}
