package edu.java.dto.api.response;

import jakarta.validation.constraints.NotBlank;

public record ApiErrorResponse(
    @NotBlank String description,
    @NotBlank String code,
    @NotBlank String exceptionName,
    @NotBlank String exceptionMessage
) {
}
