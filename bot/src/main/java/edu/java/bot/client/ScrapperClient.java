package edu.java.bot.client;

import edu.java.bot.dto.scrapper.request.AddChatRequest;
import edu.java.bot.dto.scrapper.request.AddLinkRequest;
import edu.java.bot.dto.scrapper.request.RemoveLinkRequest;
import edu.java.bot.dto.scrapper.response.LinkResponse;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import org.springframework.http.ResponseEntity;

public interface ScrapperClient {
    void removeChat(Long id);

    void addChat(Long id, AddChatRequest addChatRequest);

    ResponseEntity<LinkResponse> removeLink(Long id, RemoveLinkRequest removeLinkRequest);

    ResponseEntity<ListLinksResponse> getLinks(Long id);

    ResponseEntity<LinkResponse> addLink(Long id, AddLinkRequest addLinkRequest);
}
