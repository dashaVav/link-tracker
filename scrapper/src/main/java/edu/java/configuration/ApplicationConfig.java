package edu.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfig {
    @Bean
    public ApplicationProperties.Scheduler scheduler(ApplicationProperties properties) {
        return properties.scheduler();
    }
}
