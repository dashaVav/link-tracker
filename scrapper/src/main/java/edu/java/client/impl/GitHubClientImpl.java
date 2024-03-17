package edu.java.client.impl;

import edu.java.client.GitHubClient;
import edu.java.dto.github.GitHubDTO;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;

    public GitHubClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public List<GitHubDTO> fetchRepo(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/events", owner, repo)
            .retrieve()
            .bodyToFlux(GitHubDTO.class)
            .collectList()
            .block();
    }
}
