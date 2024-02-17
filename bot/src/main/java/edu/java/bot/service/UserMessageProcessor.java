package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comands.Command;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.MessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMessageProcessor {
    private final ChatRepository repository;
    private final Command helpCommand;
    private final Command trackCommand;
    private final Command untrackCommand;
    private final Command listCommand;
    private final Command startCommand;

    public SendMessage handleUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            Long chatId = update.message().chat().id();
            String command = MessageUtils.getCommand(update.message().text());
            switch (command) {
                case "/start":
                    return startCommand.handle(update);
                case "/help":
                    return helpCommand.handle(update);
                case "/track":
                    return trackCommand.handle(update);
                case "/untrack":
                    return untrackCommand.handle(update);
                case "/list":
                    return listCommand.handle(update);
                default:
                    return new SendMessage(chatId, "Неизвестная команда");
            }
        }
        return new SendMessage(update.message().chat().id(), "хуйню сделал");
    }

}
