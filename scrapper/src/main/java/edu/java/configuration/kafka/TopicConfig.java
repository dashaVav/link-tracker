package edu.java.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topic}")
    private String updatesTopic;

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(updatesTopic, 1, (short) 1);
    }
}
