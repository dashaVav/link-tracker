package edu.java.client;

import edu.java.dto.bot.LinkUpdateResponse;

public interface BotClient {
    void sendUpdate(LinkUpdateResponse linkUpdateRequest);
}
