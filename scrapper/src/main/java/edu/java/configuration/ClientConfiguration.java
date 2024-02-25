package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Value("${api.git-base-url}")
    private String gitBaseUrl;

    @Value("${api.stackoverflow-base-url}")
    private String stackOverflowUrl;

    @Bean
    public GitHubClient gitHubWebClient() {
        return new GitHubClient(gitBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClient(stackOverflowUrl);
    }
}
