package edu.java.service.jdbc;

import edu.java.domain.repositoty.JdbcLinksRepository;
import edu.java.exception.custom.LinkNotFoundException;
import edu.java.exception.custom.ReAddingLinkException;
import edu.java.model.Link;
import edu.java.service.LinkService;
import edu.java.service.TgChatService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {
    private final JdbcLinksRepository linksRepository;

    private final TgChatService jdbcTgChatService;

    @Override
    public Link add(long tgChatId, URI url) {
        String urlStr = url.toString();

        jdbcTgChatService.checkChatExists(tgChatId);
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
        jdbcTgChatService.checkChatExists(tgChatId);

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
        jdbcTgChatService.checkChatExists(tgChatId);
        return linksRepository.findAllByChatId(tgChatId);
    }
}
