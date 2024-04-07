package edu.java.bot.service;

import edu.java.bot.dto.api.LinkUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagesKafkaListener {
    private final UpdateProcessor updateProcessor;
    private final DlqProducer dlqProducer;

    @KafkaListener(topics = "${topic}",
                   groupId = "${group-id}",
                   containerFactory = "factory")
    public void listenStringMessages(@Payload LinkUpdateResponse updates) {
        updateProcessor.handleUpdates(updates);
    }
}

