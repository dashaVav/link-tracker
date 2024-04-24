package edu.java.service.jpa;

import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.LinkNotFoundException;
import edu.java.exception.custom.ReAddingLinkException;
import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repositoty.jpa.JpaChatRepository;
import edu.java.repositoty.jpa.JpaLinkRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository jpaLinkRepository;
    private final JpaChatRepository jpaChatRepository;
    private static final String CHAT_NOT_FOUND_MESSAGE = "Chat with id %d not found";

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new ChatIdNotFoundException(String.format(CHAT_NOT_FOUND_MESSAGE, tgChatId)));

        if (chat.getLinks().stream().anyMatch(link -> link.getUrl().equals(url))) {
            throw new ReAddingLinkException(String.format("Link with url %s already added for chat", url));
        }
        Link link;
        if (jpaLinkRepository.findByUrl(url).isEmpty()) {
            link = jpaLinkRepository.save(new Link(url, OffsetDateTime.now()));
        } else {
            link = jpaLinkRepository.findByUrl(url).get();
        }

        chat.getLinks().add(link);
        jpaChatRepository.save(chat);
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Chat chat = jpaChatRepository.findById(tgChatId)
            .orElseThrow(() -> new ChatIdNotFoundException(String.format(CHAT_NOT_FOUND_MESSAGE, tgChatId)));
        Link link = jpaLinkRepository.findByUrl(url)
            .orElseThrow(() -> new LinkNotFoundException(String.format("Link with url %s not found", url.toString())));
        chat.getLinks().remove(link);

        if (link.getChats().size() == 1) {
            jpaLinkRepository.delete(link);
        }
        return link;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return jpaChatRepository.findById(tgChatId)
            .map(Chat::getLinks)
            .orElseThrow(() -> new ChatIdNotFoundException(String.format(CHAT_NOT_FOUND_MESSAGE, tgChatId))).stream()
            .toList();
    }
}
