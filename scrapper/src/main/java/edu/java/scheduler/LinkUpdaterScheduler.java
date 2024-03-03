package edu.java.scheduler;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;

    private static final String OWNER_GITHUB_REPO = "DashaVav";
    private static final String GITHUB_REPO = "java-course-2023";
    private static final long STACK_OVERFLOW_QUESTION_ID = 6591213L;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
//        LOGGER.info(gitHubClient.fetchRepo(OWNER_GITHUB_REPO, GITHUB_REPO));
//        LOGGER.info(stackOverflowClient.fetchQuestion(STACK_OVERFLOW_QUESTION_ID));
    }
}
