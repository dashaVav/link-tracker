package edu.java.service.jdbc;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.domain.model.Link;
import edu.java.domain.repositoty.LinksRepository;
import edu.java.dto.bot.LinkUpdateResponse;
import edu.java.dto.github.GitHubDTO;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import edu.java.service.LinkUpdater;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcUpdater implements LinkUpdater {
    private final LinksRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    private static final Duration UPDATE_TIME = Duration.ofSeconds(1);
    private static final String MESSAGE = "Update";

    @Override
    public void update() {
        List<Link> linksToCheck = linkRepository.findLinksToCheck(LocalDateTime.now().minus(UPDATE_TIME));
        for (Link link : linksToCheck) {
            if (link.getUrl().toString().contains("github.com")) {
                gitHibProcess(link);
            } else if (link.getUrl().toString().contains("stackoverflow.com")) {
                stackOverflowProcess(link);
            }
            linkRepository.updateCheckAt(OffsetDateTime.now(), link.getId());
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private void gitHibProcess(Link link) {
        String[] parts = link.getUrl().toString().split("/");
        GitHubDTO repo = gitHubClient.fetchRepo(parts[3], parts[4]);
        if (repo != null && repo.pushedAt().isAfter(link.getCheckedAt())) {
            botClient.sendUpdate(
                new LinkUpdateResponse(
                    link.getId(),
                    link.getUrl(),
                    MESSAGE,
                    linkRepository.tgChatIdsByLinkId(link.getId())
                ));
        }
    }


    @SuppressWarnings("checkstyle:MagicNumber")
    private void stackOverflowProcess(Link link) {
        Long questionId = Long.parseLong(link.getUrl().toString().split("/")[4]);
        StackOverflowDTO question = stackOverflowClient.fetchQuestion(questionId);
        if (question != null && question.items().getFirst().lastActivityDate().isAfter(link.getCheckedAt())) {
            botClient.sendUpdate(
                new LinkUpdateResponse(
                    link.getId(),
                    link.getUrl(),
                    MESSAGE,
                    linkRepository.tgChatIdsByLinkId(link.getId())
                ));
        }
    }

}


