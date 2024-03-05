package edu.java.controller.impl;

import edu.java.controller.TgChatApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TgChatApiController implements TgChatApi {
    @Override
    public ResponseEntity<Void> removeChat(Long id) {
        //todo delete chat
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> addChat(Long id) {
        //todo create chat
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
