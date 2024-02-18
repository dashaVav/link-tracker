package edu.java.bot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.comands.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotInitializer {
    public final TelegramBot bot;

    @Autowired
    public BotInitializer(ApplicationConfig config, UserMessageProcessor userMessageProcessor, List<Command> commands) {
        bot = new TelegramBot(config.telegramToken());
        setMyCommands(commands);

        bot.setUpdatesListener(updates -> {
            process(userMessageProcessor, updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void process(UserMessageProcessor userMessageProcessor, List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null && update.message().text() != null) {
                bot.execute(userMessageProcessor.handleUpdate(update));
            }
        });
    }

    private void setMyCommands(List<Command> commands) {
        List<BotCommand> botCommands = commands.stream().map(Command::toApiCommand).toList();
        SetMyCommands setMyCommands = new SetMyCommands(botCommands.toArray(BotCommand[]::new));
        bot.execute(setMyCommands);
    }
}
