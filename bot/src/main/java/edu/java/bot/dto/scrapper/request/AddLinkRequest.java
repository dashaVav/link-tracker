package edu.java.bot.dto.scrapper.request;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record AddLinkRequest(
    @NotNull URI link
) {
}
