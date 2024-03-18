package edu.java.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public record StackOverflowDTO(List<Item> items) {
    public record Item(
        @JsonProperty("owner")
        StackOverflowOwner owner,

        @NotNull
        @JsonProperty("creation_date")
        OffsetDateTime creationDate,

        @NotNull
        @JsonProperty("question_id")
        Long questionId
    ) {
    }
}
