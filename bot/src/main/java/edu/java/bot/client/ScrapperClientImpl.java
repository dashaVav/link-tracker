package edu.java.bot.client;

import edu.java.bot.configuration.retry.RetryProperties;
import edu.java.bot.dto.api.ApiErrorResponse;
import edu.java.bot.dto.scrapper.request.AddChatRequest;
import edu.java.bot.dto.scrapper.request.AddLinkRequest;
import edu.java.bot.dto.scrapper.request.RemoveLinkRequest;
import edu.java.bot.dto.scrapper.response.LinkResponse;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import edu.java.bot.exception.api.ApiBadRequestException;
import edu.java.bot.exception.api.ApiNotFoundException;
import edu.java.bot.exception.api.ApiReAddingException;
import edu.java.bot.exception.api.ResourceUnavailableException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClientImpl implements ScrapperClient {
    private final WebClient webClient;
    private static final String HEADER_TG_CHAT_ID = "Tg-Chat-Id";
    private static final String TG_CHAT_PATH = "/tg-chat/";
    private static final String LINKS = "/links";
    private final RetryProperties retryProperties;

    public ScrapperClientImpl(String baseUrl, RetryProperties retryProperties) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryProperties = retryProperties;
    }

    @Override
    public void removeChat(Long id) {
        webClient.delete()
            .uri(TG_CHAT_PATH + id)
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException())
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiNotFoundException::new)
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(Void.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public void addChat(Long id, AddChatRequest addChatRequest) {
        webClient.post()
            .uri(TG_CHAT_PATH + id)
            .body(BodyInserters.fromValue(addChatRequest))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException())
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiNotFoundException::new)
            )
            .onStatus(
                HttpStatus.CONFLICT::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiReAddingException::new)
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(Void.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public ResponseEntity<LinkResponse> removeLink(Long id, RemoveLinkRequest removeLinkRequest) {
        return webClient.method(HttpMethod.DELETE)
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(removeLinkRequest))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException())
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiNotFoundException::new)
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(LinkResponse.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long id) {
        return webClient.get()
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException())
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiNotFoundException::new)
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(ListLinksResponse.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(Long id, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(LINKS)
            .header(HEADER_TG_CHAT_ID, String.valueOf(id))
            .body(BodyInserters.fromValue(addLinkRequest))
            .retrieve()
            .onStatus(
                code -> retryProperties.getStatusCode().contains(code),
                response -> Mono.error(new ResourceUnavailableException())
            )
            .onStatus(
                HttpStatus.NOT_FOUND::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiNotFoundException::new)
            )
            .onStatus(
                HttpStatus.CONFLICT::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiReAddingException::new)
            )
            .onStatus(
                HttpStatus.BAD_REQUEST::equals,
                response -> response.bodyToMono(ApiErrorResponse.class).map(ApiBadRequestException::new)
            )
            .toEntity(LinkResponse.class)
            .retryWhen(retryProperties.getRetry())
            .block();
    }

}
