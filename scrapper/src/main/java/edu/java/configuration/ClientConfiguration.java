package edu.java.configuration;

import edu.java.clients.BotClient;
import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.clients.impl.BotClientImpl;
import edu.java.clients.impl.GitHubClientImpl;
import edu.java.clients.impl.StackOverflowClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Value("${api.git-hub.base-url:${api.git-hub.default-url}}")
    private String githubBaseUrl;

    @Bean
    public GitHubClient gitHubWebClient() {
        return new GitHubClientImpl(githubBaseUrl);
    }

    @Value("${api.stackoverflow.base-url:${api.stackoverflow.default-url}}")
    private String stackoverflowBaseUrl;

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClientImpl(stackoverflowBaseUrl);
    }

    @Value("${api.bot.base-url}")
    private String botBaseUrl;

    @Bean
    public BotClient botClient() {
        return new BotClientImpl(botBaseUrl);
    }
}
