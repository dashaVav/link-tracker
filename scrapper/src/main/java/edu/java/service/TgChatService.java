package edu.java.service;

import edu.java.dto.api.request.AddChatRequest;

public interface TgChatService {
    void register(long tgChatId, AddChatRequest addChatRequest);

    void unregister(long tgChatId);

    void checkChatExists(long tgChatId);
}
