package edu.java.bot.controller.impl;

import edu.java.bot.controller.UpdatesApi;
import edu.java.bot.dto.api.LinkUpdateRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateController implements UpdatesApi {
    private final static Logger LOGGER = LogManager.getLogger();

    @Override
    public ResponseEntity<Void> sendUpdate(LinkUpdateRequest linkUpdate) {
        LOGGER.info(linkUpdate);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
