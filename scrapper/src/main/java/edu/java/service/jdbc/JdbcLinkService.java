package edu.java.service.jdbc;

import edu.java.domain.model.Chat;
import edu.java.domain.model.Link;
import edu.java.domain.model.LinkChat;
import edu.java.domain.repositoty.ChatsRepository;
import edu.java.domain.repositoty.LinkChatRepository;
import edu.java.domain.repositoty.LinksRepository;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.LinkNotFoundException;
import edu.java.exception.custom.ReAddingLinkException;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final LinksRepository linksRepository;
    private final LinkChatRepository linkChatRepository;
    private final ChatsRepository chatsRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        String urlStr = url.toString();
        Optional<Chat> chatOptional = chatsRepository.findChatById(tgChatId);
        if (chatOptional.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(urlStr);
        if (linkOptional.isEmpty()) {
            linksRepository.add(urlStr);
        }
        long linkId = linksRepository.getLinkIdByUrl(urlStr);

        Optional<LinkChat> foundPair = linkChatRepository.findPairLinkChat(linkId, tgChatId);
        if (foundPair.isPresent()) {
            throw new ReAddingLinkException(String.format("Link with url %s already added for chat", urlStr));
        }

        linkChatRepository.add(linkId, tgChatId);

        return linksRepository.findById(linkId);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Optional<Chat> chatOptional = chatsRepository.findChatById(tgChatId);
        if (chatOptional.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(url.toString());
        if (linkOptional.isEmpty()) {
            throw new LinkNotFoundException(String.format("Link with url %s not found", url));
        }

        long linkId = linkOptional.get().getId();

        linkChatRepository.remove(linkId, tgChatId);
        if (linkChatRepository.findAllByLinkId(linkId).isEmpty()) {
            linksRepository.remove(linkId);
        }
        return linksRepository.findById(linkId);
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        Optional<Chat> chatOptional = chatsRepository.findChatById(tgChatId);
        if (chatOptional.isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }

        List<Link> result = new ArrayList<>();
        List<LinkChat> list = linkChatRepository.findAllByChatId(tgChatId);
        for (LinkChat linkChat : list) {
            result.add(linksRepository.findById(linkChat.linkId()));
        }
        return result;
    }
}
