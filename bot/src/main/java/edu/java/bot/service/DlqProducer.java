package edu.java.bot.service;

import edu.java.bot.dto.api.LinkUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DlqProducer {

    private final KafkaTemplate<Long, LinkUpdateResponse> kafkaProducer;

    @Value("${spring.kafka.dlq-topic}")
    private String topic;

    public void send(LinkUpdateResponse update) {
        kafkaProducer.send(topic, update);
    }
}
