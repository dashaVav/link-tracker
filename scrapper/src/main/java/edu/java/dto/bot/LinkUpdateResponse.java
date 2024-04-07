package edu.java.dto.bot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LinkUpdateResponse {
    private final Long id;
    private final @NotNull URI url;
    private final @NotBlank String description;
    private final @NotEmpty List<Long> tgChatIds;
}

