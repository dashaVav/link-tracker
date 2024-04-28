package edu.java.service.jpa;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverflowClient;
import edu.java.dto.bot.LinkUpdateResponse;
import edu.java.dto.github.GitHubDTO;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repositoty.jpa.JpaLinkRepository;
import edu.java.service.LinkUpdater;
import edu.java.service.scheduler.NotificationService;
import io.micrometer.core.instrument.Counter;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {
    private final JpaLinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final NotificationService notificationService;
    private final Counter processedUpdatesCounter;
    private static final Logger LOGGER = LogManager.getLogger();

    private static final Duration UPDATE_TIME = Duration.ofHours(1);

    @Override
    @Transactional
    public void update() {
        OffsetDateTime time = OffsetDateTime.now().minus(UPDATE_TIME);
        List<Link> linksToCheck = linkRepository.findLinksToCheck(time);
        for (Link link : linksToCheck) {
            if (link.getUrl().toString().contains("github.com")) {
                gitHibProcess(link);
            } else if (link.getUrl().toString().contains("stackoverflow.com")) {
                stackOverflowProcess(link);
            }
            linkRepository.updateCheckedAtById(link.getId(), time);
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private void gitHibProcess(Link link) {
        String[] parts = link.getUrl().toString().split("/");
        List<GitHubDTO> repo;
        try {
            repo = gitHubClient.fetchRepo(parts[3], parts[4]);
        } catch (Exception e) {
            LOGGER.warn(e);
            return;
        }

        for (GitHubDTO event : repo.reversed()) {
            if (event.createdAt() != null && event.createdAt().isAfter(link.getCheckedAt())) {
                String message = gitHubClient.getMessage(event);
                if (!message.isEmpty()) {
                    LinkUpdateResponse linkUpdateResponse = new LinkUpdateResponse(
                        link.getId(),
                        link.getUrl(),
                        message,
                        linkRepository.findChatIdsForLink(link.getId())
                    );
                    notificationService.sendNotification(linkUpdateResponse);
                    processedUpdatesCounter.increment();
                }
            }
        }
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private void stackOverflowProcess(Link link) {
        Long questionId = Long.parseLong(link.getUrl().toString().split("/")[4]);
        StackOverflowDTO answers;
        try {
            answers = stackOverflowClient.fetchAnswersByQuestionId(questionId);
        } catch (Exception e) {
            LOGGER.warn(e);
            return;
        }

        for (StackOverflowDTO.Item event : answers.items().reversed()) {
            if (event.creationDate().isAfter(link.getCheckedAt())) {
                String message = stackOverflowClient.getMessage(event);
                if (!message.isEmpty()) {
                    LinkUpdateResponse linkUpdateResponse = new LinkUpdateResponse(
                        link.getId(),
                        link.getUrl(),
                        stackOverflowClient.getMessage(event),
                        linkRepository.findById(link.getId()).get()
                            .getChats().stream().map(Chat::getId).toList()
                    );
                    notificationService.sendNotification(linkUpdateResponse);
                    processedUpdatesCounter.increment();
                }
            }
        }
    }

}
