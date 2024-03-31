package edu.java.bot.dto.api;

import jakarta.validation.constraints.NotBlank;

public record ApiErrorResponse(
    @NotBlank String description,
    @NotBlank String code,
    @NotBlank String exceptionName,
    @NotBlank String exceptionMessage
) {
}
