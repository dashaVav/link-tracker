package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.BotClientImpl;
import edu.java.client.impl.GitHubClientImpl;
import edu.java.client.impl.StackOverflowClientImpl;
import edu.java.configuration.retry.RetryProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Value("${api.git-hub.base-url:${api.git-hub.default-url}}")
    private String githubBaseUrl;

    @Bean
    public GitHubClient gitHubWebClient(RetryProperties retryProperties) {
        return new GitHubClientImpl(githubBaseUrl, retryProperties);
    }

    @Value("${api.stackoverflow.base-url:${api.stackoverflow.default-url}}")
    private String stackoverflowBaseUrl;

    @Bean
    public StackOverflowClient stackOverflowClient(RetryProperties retryProperties) {
        return new StackOverflowClientImpl(stackoverflowBaseUrl, retryProperties);
    }

    @Value("${api.bot.base-url}")
    private String botBaseUrl;

    @Bean
    public BotClient botClient(RetryProperties retryProperties) {
        return new BotClientImpl(botBaseUrl, retryProperties);
    }
}
