package edu.java.bot.client;

import edu.java.bot.dto.scrapper.request.AddLinkRequest;
import edu.java.bot.dto.scrapper.request.RemoveLinkRequest;
import edu.java.bot.dto.scrapper.response.LinkResponse;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperClientImpl implements ScrapperClient {
    private final WebClient webClient;
    private static final String HEADER_TG_CHAT_ID = "Tg-Chat-Id";
    private static final String TG_CHAT_PATH = "/tg-chat/";
    private static final String LINKS = "/links";

    public ScrapperClientImpl(String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public ResponseEntity<Void> removeChat(Long id) {
        return webClient.delete()
            .uri(TG_CHAT_PATH + id)
            .retrieve()
            .toEntity(Void.class)
            .block();
    }

    @Override
    public ResponseEntity<Void> addChat(Long id) {
        return webClient.post()
            .uri(TG_CHAT_PATH + id)
            .retrieve()
            .toEntity(Void.class)
            .block();
    }

    @Override
    public ResponseEntity<LinkResponse> removeLink(Long id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        return webClient.get()
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .retrieve()
            .toEntity(ListLinksResponse.class)
            .block();
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(Long id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

}
