package edu.java.bot.configuration.kafka;

import edu.java.bot.dto.api.LinkUpdateRequest;
import edu.java.bot.service.UpdateProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagesKafkaListener {
    private final UpdateProcessor updateProcessor;

    @KafkaListener(topics = "${spring.kafka.topic}",
                   groupId = "${spring.kafka.group-id}")
    public void listenStringMessages(ConsumerRecord<Long, LinkUpdateRequest> updates) {
        updateProcessor.handleUpdates(updates.value());
    }
}

