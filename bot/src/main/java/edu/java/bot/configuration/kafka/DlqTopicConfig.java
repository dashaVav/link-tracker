package edu.java.bot.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DlqTopicConfig {

    @Value("${spring.kafka.dlq-topic}")
    private String dqlTopic;

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(dqlTopic, 2, (short) 1);
    }
}
