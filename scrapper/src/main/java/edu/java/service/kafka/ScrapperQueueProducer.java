package edu.java.service.kafka;

import edu.java.dto.bot.LinkUpdateResponse;
import edu.java.service.scheduler.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScrapperQueueProducer implements NotificationService {
    private final KafkaTemplate<Long, LinkUpdateResponse> kafkaProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Override
    public void sendNotification(LinkUpdateResponse update) {
        kafkaProducer.send(topic, update);
    }
}
