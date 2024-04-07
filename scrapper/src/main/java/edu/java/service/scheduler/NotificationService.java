package edu.java.service.scheduler;

import edu.java.dto.bot.LinkUpdateResponse;

public interface NotificationService {
    void sendNotification(LinkUpdateResponse update);
}
