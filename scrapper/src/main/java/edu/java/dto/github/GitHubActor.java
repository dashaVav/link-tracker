package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubActor(
    @JsonProperty("login")
    String login,

    @JsonProperty("id")
    long id
) {
}
