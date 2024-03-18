package edu.java.client.impl;

import edu.java.client.GitHubClient;
import edu.java.dto.github.GitHubDTO;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;
    private final static String OPENED = "opened";

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

    @Override
    public String getMessage(GitHubDTO event) {
        String message;
        switch (event.type()) {
            case "CreateEvent" -> {
                message = String.format("%s создал ветку %s", event.actor().login(), event.payload().branchName());
            }
            case "DeleteEvent" -> {
                message = String.format("%s удалил ветку %s", event.actor().login(), event.payload().branchName());
            }
            case "IssuesEvent" -> {
                if (event.payload().action().equals(OPENED)) {
                    message =
                        String.format("%s открыл тикет \"%s\"", event.actor().login(), event.payload().issue().title());
                } else {
                    message =
                        String.format("%s закрыл тикет \"%s\"", event.actor().login(), event.payload().issue().title());
                }
            }
            case "PullRequestEvent" -> {
                if (event.payload().action().equals(OPENED)) {
                    message =
                        String.format(
                            "%s открыл pull request \"%s\"",
                            event.actor().login(),
                            event.payload().pullRequest().title()
                        );
                } else {
                    message =
                        String.format(
                            "%s закрыл pull request \"%s\"",
                            event.actor().login(),
                            event.payload().pullRequest().title()
                        );
                }
            }
            case "PushEvent" -> {
                message = String.format(
                    "%s сделал commit \"%s\" на ветке %s",
                    event.actor().login(),
                    event.payload().commits().getFirst().message(),
                    event.payload().branchName().split("/")[2]
                );
            }
            default -> {
                message = "";
            }
        }
        return message;
    }
}
