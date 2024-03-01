package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubDTO(
    @JsonProperty("owner")
    GitHubOwner owner,

    @JsonProperty("full_name")
    String fullName,

    @JsonProperty("pushed_at")
    OffsetDateTime pushedAt
) {
}
