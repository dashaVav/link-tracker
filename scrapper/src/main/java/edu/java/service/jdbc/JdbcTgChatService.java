package edu.java.service.jdbc;

import edu.java.dto.api.request.AddChatRequest;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.ReRegistrationException;
import edu.java.model.Chat;
import edu.java.repositoty.jdbc.JdbcChatsRepository;
import edu.java.service.TgChatService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatsRepository jdbcChatsRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        Optional<Chat> foundChat = jdbcChatsRepository.findChatById(tgChatId);
        if (foundChat.isPresent()) {
            throw new ReRegistrationException(String.format("Chat with id %d already added", tgChatId));
        }

        jdbcChatsRepository.add(tgChatId, addChatRequest.userName());
    }

    @Override
    public void unregister(long tgChatId) {
        if (jdbcChatsRepository.findChatById(tgChatId).isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }

        jdbcChatsRepository.remove(tgChatId);
    }
}
