package edu.java.controller.impl;

import edu.java.controller.TgChatApi;
import edu.java.dto.api.request.AddChatRequest;
import edu.java.service.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TgChatApiController implements TgChatApi {
    private final TgChatService tgChatService;

    @Override
    public ResponseEntity<Void> removeChat(Long tgChatId) {
        tgChatService.unregister(tgChatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addChat(Long tgChatId, AddChatRequest addChatRequest) {
        tgChatService.register(tgChatId, addChatRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
