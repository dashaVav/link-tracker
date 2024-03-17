package edu.java.service.scheduler;

import edu.java.service.updater.LinkUpdaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private final LinkUpdaterService linkUpdateService;

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {

        linkUpdateService.update();

    }
}
