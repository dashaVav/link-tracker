package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

public record GitHubDTO(
    @JsonProperty("actor")
    GitHubActor actor,

    @JsonProperty("type")
    String type,

    @JsonProperty("repo")
    GitHubRepo repo,

    @JsonProperty("created_at")
    OffsetDateTime createdAt,

    @JsonProperty("payload")
    Payload payload

) {
    public record GitHubActor(
        @JsonProperty("login")
        String login
    ) {
    }

    public record GitHubRepo(
        @JsonProperty("url")
        String url
    ) {
    }

    public record Payload(
        @JsonProperty("commits")
        List<Commits> commits,

        @JsonProperty("ref")
        String branchName,

        @JsonProperty("action")
        String action,

        @JsonProperty("pull_request")
        PullRequest pullRequest,

        @JsonProperty("issue")
        Issue issue
    ) {
        public record Commits(
            @JsonProperty("message")
            String message
        ) {
        }

        public record PullRequest(
            @JsonProperty("title")
            String title
        ) {
        }

        public record Issue(
            @JsonProperty("title")
            String title
        ) {
        }
    }
}
