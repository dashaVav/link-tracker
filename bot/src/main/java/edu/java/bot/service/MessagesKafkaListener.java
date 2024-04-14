package edu.java.bot.service;

import edu.java.bot.dto.api.LinkUpdateResponse;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagesKafkaListener {
    private final UpdateProcessor updateProcessor;
    private final DlqProducer dlqProducer;
    private final Counter processedUpdatesCounter;

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.group-id}",
                   containerFactory = "factory")
    public void listenStringMessages(@Payload LinkUpdateResponse updates) {
        try {
            updateProcessor.handleUpdates(updates);
            processedUpdatesCounter.increment();
        } catch (Exception e) {
            dlqProducer.send(updates);
        }
    }
}

