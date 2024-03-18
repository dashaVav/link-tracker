package edu.java.service.jdbc;

import edu.java.domain.model.Chat;
import edu.java.domain.repositoty.JdbcChatsRepository;
import edu.java.dto.api.request.AddChatRequest;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.ReRegistrationException;
import edu.java.service.TgChatService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatsRepository chatsRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        Optional<Chat> foundChat = chatsRepository.findChatById(tgChatId);
        if (foundChat.isPresent()) {
            throw new ReRegistrationException(String.format("Chat with id %d already added", tgChatId));
        }

        chatsRepository.add(tgChatId, addChatRequest.userName());
    }

    @Override
    public void unregister(long tgChatId) {
        checkChatExists(tgChatId);
        chatsRepository.remove(tgChatId);
    }

    @Override
    public void checkChatExists(long tgChatId) {
        Optional<Chat> existingChat = chatsRepository.findChatById(tgChatId);
        if (existingChat.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }
    }
}
