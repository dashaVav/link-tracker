package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubRepo(
    @JsonProperty("url")
    String url
) {
}
