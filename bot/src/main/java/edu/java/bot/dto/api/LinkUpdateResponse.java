package edu.java.bot.dto.api;

import java.net.URI;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkUpdateResponse {
    private Long id;
    private URI url;
    private String description;
    private List<Long> tgChatIds;
}
