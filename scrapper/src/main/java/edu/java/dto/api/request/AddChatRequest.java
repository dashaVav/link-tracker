package edu.java.dto.api.request;

import jakarta.validation.constraints.NotNull;

public record AddChatRequest(
    @NotNull String userName
) {
}
