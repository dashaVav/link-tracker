package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.bot.BotInitializer;
import edu.java.bot.dto.api.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateProcessor {
    private final BotInitializer bot;

    public void handleUpdates(LinkUpdateRequest update) {
        for (long id : update.tgChatIds()) {
            bot.sendUpdate(new SendMessage(id, update.description() + "\n" + update.url()));
        }
    }
}
