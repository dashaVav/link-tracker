package edu.java.controller.impl;

import edu.java.controller.LinksApi;
import edu.java.dto.api.request.AddLinkRequest;
import edu.java.dto.api.request.RemoveLinkRequest;
import edu.java.dto.api.response.LinkResponse;
import edu.java.dto.api.response.ListLinksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinksApiController implements LinksApi {
    private final static URI DEFAULT_LINK = URI.create("link");
    private final static Long DEFAULT_ID = 1L;
    @Override
    public ResponseEntity<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        //todo remove links
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, removeLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long tgChatId) {
        //todo get links
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, DEFAULT_LINK);
        List<LinkResponse> linkResponseList = List.of(linkResponse);
        ListLinksResponse listLinksResponse = new ListLinksResponse(linkResponseList, 1);
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        //todo add link
        LinkResponse linkResponse = new LinkResponse(DEFAULT_ID, addLinkRequest.link());
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
