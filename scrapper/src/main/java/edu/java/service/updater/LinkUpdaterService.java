package edu.java.service.updater;

import edu.java.client.BotClient;
import edu.java.client.GitHubClient;
import edu.java.domain.model.Link;
import edu.java.dto.bot.LinkUpdateResponse;
import edu.java.dto.github.GitHubDTO;
import edu.java.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import edu.java.domain.repositoty.LinksRepository;

@Service
@RequiredArgsConstructor
public class LinkUpdaterService implements LinkUpdater {
    private final LinksRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final BotClient botClient;

    @Override
    public void update() {
        List<Link> linksToCheck = linkRepository.findLinksToCheck(LocalDateTime.now().minusSeconds(1l));

        for (Link link : linksToCheck) {
            System.out.println(link);
            if (link.getUrl().toString().startsWith("https://github.com/")) {
                String[] parts = link.getUrl().toString().split("/");
                GitHubDTO repo = gitHubClient.fetchRepo(parts[3], parts[4]);
                if (repo != null && repo.pushedAt().isAfter(link.getCheckedAt())) {
                    botClient.sendUpdate(new LinkUpdateResponse(1L, link.getUrl(), "+", List.of(1l)));
                }
            }
//            else if (link.getUri().startsWith("https://stackoverflow.com/questions/")) {
//                Long questionId = Long.parseLong(link.getUri().split("/")[4]);
//                StackOverflowDTO question = stackOverflowClient.fetchQuestion(questionId);
//                if (question != null && question.getLastActivityDate().isAfter(link.getCheckedAt())) {
//                    botClient.notifyUpdate("Есть обновление для ссылки: " + link.getUri());
//                }
//            }
        }
    }
}
