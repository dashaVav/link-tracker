package edu.java.client.impl;

import edu.java.client.GitHubClient;
import edu.java.configuration.retry.RetryProperties;
import edu.java.dto.github.GitHubDTO;
import edu.java.exception.custom.ResourceUnavailableException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubClientImpl implements GitHubClient {
    private final WebClient webClient;
    private final static String OPENED = "opened";
    private final RetryProperties retryProperties;

    public GitHubClientImpl(String baseUrl, RetryProperties retryProperties) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryProperties = retryProperties;
    }

    @Override
    public List<GitHubDTO> fetchRepo(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/events", owner, repo)
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException(
                    "Try later",
                    (HttpStatus) response.statusCode()
                ))
            )
            .bodyToFlux(GitHubDTO.class)
            .collectList()
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public String getMessage(GitHubDTO event) {
        String message;
        switch (event.type()) {
            case "CreateEvent" -> {
                message = String.format("%s создал(а) ветку %s", event.actor().login(), event.payload().branchName());
            }
            case "DeleteEvent" -> {
                message = String.format("%s удалил(а) ветку %s", event.actor().login(), event.payload().branchName());
            }
            case "IssuesEvent" -> {
                if (event.payload().action().equals(OPENED)) {
                    message =
                        String.format("%s открыл(а) тикет \"%s\"",
                            event.actor().login(), event.payload().issue().title()
                        );
                } else {
                    message =
                        String.format("%s закрыл(а) тикет \"%s\"",
                            event.actor().login(), event.payload().issue().title()
                        );
                }
            }
            case "PullRequestEvent" -> {
                if (event.payload().action().equals(OPENED)) {
                    message =
                        String.format(
                            "%s открыл(а) pull request \"%s\"",
                            event.actor().login(),
                            event.payload().pullRequest().title()
                        );
                } else {
                    message =
                        String.format(
                            "%s закрыл(а) pull request \"%s\"",
                            event.actor().login(),
                            event.payload().pullRequest().title()
                        );
                }
            }
            case "PushEvent" -> {
                message = String.format(
                    "%s сделал(а) commit \"%s\" на ветке %s",
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
