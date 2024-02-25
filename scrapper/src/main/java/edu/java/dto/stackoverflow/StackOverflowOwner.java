package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowOwner(
    @JsonProperty("account_id")
    long accountId,

    @JsonProperty("display_name")
    String displayName
) {
}
