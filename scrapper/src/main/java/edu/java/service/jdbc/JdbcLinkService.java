package edu.java.service.jdbc;

import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.LinkNotFoundException;
import edu.java.exception.custom.ReAddingLinkException;
import edu.java.model.Link;
import edu.java.repositoty.jdbc.JdbcChatsRepository;
import edu.java.repositoty.jdbc.JdbcLinksRepository;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcChatsRepository chatsRepository;
    private final JdbcLinksRepository linksRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        String urlStr = url.toString();
        checkChatExists(tgChatId);
        if (linksRepository.isLinkAlreadyAddedForChat(urlStr, tgChatId)) {
            throw new ReAddingLinkException(String.format("Link with url %s already added for chat", urlStr));
        }

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(urlStr);
        if (linkOptional.isEmpty()) {
            linksRepository.add(urlStr);
        }
        long linkId = linksRepository.findLinkByUrl(urlStr).get().getId();
        linksRepository.addRelationship(linkId, tgChatId);
        return linksRepository.findById(linkId);
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        checkChatExists(tgChatId);

        Optional<Link> linkOptional = linksRepository.findLinkByUrl(url.toString());
        if (linkOptional.isEmpty()) {
            throw new LinkNotFoundException(String.format("Link with url %s not found", url));
        }

        long linkId = linkOptional.get().getId();
        Link deletedLink = linksRepository.findById(linkId);
        linksRepository.remove(tgChatId, linkId);
        return deletedLink;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        checkChatExists(tgChatId);
        return linksRepository.findAllByChatId(tgChatId);
    }

    private void checkChatExists(long tgChatId) {
        if (chatsRepository.findChatById(tgChatId).isEmpty()) {
            throw new ChatIdNotFoundException(String.format("Chat with id %d not found", tgChatId));
        }
    }
}
