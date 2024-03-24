package edu.java.bot.dto.scrapper.request;

import jakarta.validation.constraints.NotNull;

public record AddChatRequest(
    @NotNull String userName
) {
}
