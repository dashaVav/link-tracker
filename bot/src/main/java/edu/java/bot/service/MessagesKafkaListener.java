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

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.group-id}",
                   containerFactory = "factory")
    public void listenStringMessages(@Payload LinkUpdateResponse updates) {
        try {
            updateProcessor.handleUpdates(updates);
        } catch (Exception e) {
            dlqProducer.send(updates);
        }
    }
}

