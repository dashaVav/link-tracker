package edu.java.controller.impl;

import edu.java.controller.LinksApi;
import edu.java.dto.api.request.AddLinkRequest;
import edu.java.dto.api.request.RemoveLinkRequest;
import edu.java.dto.api.response.LinkResponse;
import edu.java.dto.api.response.ListLinksResponse;
import edu.java.mapper.LinkMapper;
import edu.java.service.LinkService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinksApiController implements LinksApi {
    private final LinkService jdbcLinkService;
    private final LinkMapper linkMapper;

    @Override
    public ResponseEntity<LinkResponse> removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest) {
        return new ResponseEntity<>
            (linkMapper.toDto(jdbcLinkService.remove(tgChatId, removeLinkRequest.link())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ListLinksResponse> getLinks(Long tgChatId) {
        List<LinkResponse> linkResponses = linkMapper.toDtoList(jdbcLinkService.listAll(tgChatId));
        return new ResponseEntity<>(new ListLinksResponse(linkResponses, linkResponses.size()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(Long tgChatId, AddLinkRequest addLinkRequest) {
        return new ResponseEntity<>
            (linkMapper.toDto(jdbcLinkService.add(tgChatId, addLinkRequest.link())), HttpStatus.OK);
    }
}
