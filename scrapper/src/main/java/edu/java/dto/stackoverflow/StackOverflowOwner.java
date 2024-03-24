package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowOwner(
    @JsonProperty("display_name")
    String displayName
) {
}
