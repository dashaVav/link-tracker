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
    public record Payload(
        @JsonProperty("commits")
        List<Commits> commits
    ) {
        public record Commits(
            @JsonProperty("message")
            String message
        ) {
        }
    }
}
