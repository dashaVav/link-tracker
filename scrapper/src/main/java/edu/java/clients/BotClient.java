package edu.java.clients;

import edu.java.dto.bot.LinkUpdateResponse;
import org.springframework.http.ResponseEntity;

public interface BotClient {
    ResponseEntity<Void> sendUpdate(LinkUpdateResponse linkUpdateRequest);
}
