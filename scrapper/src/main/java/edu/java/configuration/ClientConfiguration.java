package edu.java.configuration;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.BotClientImpl;
import edu.java.client.impl.GitHubClientImpl;
import edu.java.client.impl.StackOverflowClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Value("${api.git-hub.base-url:${api.git-hub.default-url}}")
    public String githubBaseUrl;

    @Bean
    public GitHubClient gitHubWebClient() {
        return new GitHubClientImpl(githubBaseUrl);
    }

    @Value("${api.stackoverflow.base-url:${api.stackoverflow.default-url}}")
    public String stackoverflowBaseUrl;

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClientImpl(stackoverflowBaseUrl);
    }

    @Value("${api.bot.base-url}")
    public String botBaseUrl;

    @Bean
    public BotClient botClient() {
        return new BotClientImpl(botBaseUrl);
    }
}
