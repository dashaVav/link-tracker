package edu.java.service.jdbc;

import edu.java.domain.model.Chat;
import edu.java.domain.model.LinkChat;
import edu.java.domain.repositoty.ChatsRepository;
import edu.java.domain.repositoty.LinkChatRepository;
import edu.java.domain.repositoty.LinksRepository;
import edu.java.dto.api.request.AddChatRequest;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.ReRegistrationException;
import edu.java.service.TgChatService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {
    private final ChatsRepository chatsRepository;
    private final LinkChatRepository linkChatRepository;
    private final LinksRepository linksRepository;

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
        Optional<Chat> foundChat = chatsRepository.findChatById(tgChatId);
        if (foundChat.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }

        List<LinkChat> linkChats = linkChatRepository.findAllByChatId(tgChatId);
        for (LinkChat linkChat : linkChats) {
            long linkId = linkChat.linkId();
            if (linkChatRepository.countChatsByLinkId(linkId) == 1) {
                linksRepository.remove(linkId);
            }
        }

        chatsRepository.remove(tgChatId);
    }
}
