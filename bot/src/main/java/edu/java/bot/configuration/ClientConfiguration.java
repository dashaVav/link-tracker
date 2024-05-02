package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.ScrapperClientImpl;
import edu.java.bot.configuration.retry.RetryProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Value("${api.scrapper.base-url}")
    private String botBaseUrl;

    @Bean
    public ScrapperClient botClient(RetryProperties retryProperties) {
        return new ScrapperClientImpl(botBaseUrl, retryProperties);
    }
}

