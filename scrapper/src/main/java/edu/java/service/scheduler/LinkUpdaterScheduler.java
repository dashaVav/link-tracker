package edu.java.service.scheduler;

import edu.java.service.jdbc.JdbcUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final JdbcUpdater linkUpdateService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        linkUpdateService.update();
    }
}


