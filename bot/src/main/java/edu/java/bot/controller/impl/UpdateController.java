package edu.java.bot.controller.impl;

import edu.java.bot.controller.UpdatesApi;
import edu.java.bot.dto.LinkUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController implements UpdatesApi {
    @Override
    public ResponseEntity<Void> updatesPost(LinkUpdate linkUpdate) {
        //todo update
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
