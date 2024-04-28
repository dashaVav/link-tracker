package edu.java.configuration.repository;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.repositoty.jdbc.JdbcChatsRepository;
import edu.java.repositoty.jdbc.JdbcLinksRepository;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcLinkUpdater;
import edu.java.service.jdbc.JdbcTgChatService;
import edu.java.service.scheduler.NotificationService;
import io.micrometer.core.instrument.Counter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkService linkService(
        JdbcLinksRepository jdbcLinksRepository,
        JdbcChatsRepository jdbcChatsRepository
    ) {
        return new JdbcLinkService(jdbcChatsRepository, jdbcLinksRepository);
    }

    @Bean
    public JdbcTgChatService tgChatService(JdbcChatsRepository jdbcChatsRepository) {
        return new JdbcTgChatService(jdbcChatsRepository);
    }

    @Bean
    public JdbcLinkUpdater updater(
        JdbcLinksRepository jdbcLinksRepository,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        NotificationService notificationService,
        Counter processedUpdatesCounter
    ) {
        return new JdbcLinkUpdater(
            jdbcLinksRepository,
            gitHubClient,
            stackOverflowClient,
            notificationService,
            processedUpdatesCounter
        );
    }
}
