package edu.java.bot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdate(
    Long id,
    @NotNull URI url,
    @NotBlank String description,
    @NotEmpty List<Long> tgChatIds
) {
}
