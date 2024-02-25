package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import edu.java.clients.impl.GitHubClientImpl;
import edu.java.clients.impl.StackOverflowClientImpl;
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
        return new GitHubClientImpl(gitBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient() {
        return new StackOverflowClientImpl(stackOverflowUrl);
    }
}
