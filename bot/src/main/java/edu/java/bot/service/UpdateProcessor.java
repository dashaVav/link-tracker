package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.BotInitializer;
import edu.java.bot.dto.api.LinkUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProcessor {
    private final BotInitializer bot;

    public void handleUpdates(LinkUpdateResponse update) {
        for (long id : update.getTgChatIds()) {
            bot.sendUpdate(new SendMessage(id, update.getDescription() + "\n" + update.getUrl()));
        }
    }
}
