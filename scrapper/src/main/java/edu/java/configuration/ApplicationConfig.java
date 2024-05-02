package edu.java.configuration;

import org.modelmapper.ModelMapper;
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

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
