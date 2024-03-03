package edu.java.bot.dto;

import jakarta.validation.constraints.NotEmpty;
import java.net.URI;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinkUpdate(
    Long id,
    @NotNull URI url,
    @NotBlank String description,
    @NotEmpty List<Long> tgChatIds
) {
}
