package edu.java.service.jpa;

import edu.java.dto.api.request.AddChatRequest;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.ReRegistrationException;
import edu.java.model.Chat;
import edu.java.repositoty.jpa.JpaChatRepository;
import edu.java.repositoty.jpa.JpaLinkRepository;
import edu.java.service.TgChatService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {
    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;

    @Override
    public void register(long tgChatId, AddChatRequest addChatRequest) {
        jpaChatRepository.findById(tgChatId).ifPresent(c -> {
            throw new ReRegistrationException(String.format("Chat with id %d already added", tgChatId));
        });

        Chat chat = new Chat(tgChatId, addChatRequest.userName());
        jpaChatRepository.save(chat);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId)));

        chat.getLinks().forEach(link -> {
            if (link.getChats().size() == 1) {
                jpaLinkRepository.delete(link);
            }
        });

        jpaChatRepository.deleteById(tgChatId);
    }
}
