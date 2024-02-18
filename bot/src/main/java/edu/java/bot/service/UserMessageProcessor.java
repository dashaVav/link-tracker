package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comands.Command;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMessageProcessor {

    @Autowired
    private List<Command> commands;
    private final ChatRepository repository;

    public SendMessage handleUpdate(Update update) {
        Long chatId = update.message().chat().id();
        String commandFromChat = CommandUtils.getCommand(update.message().text());

        for (Command commandName : commands) {
            if (commandName.command().equals(commandFromChat)) {
                if (!commandName.supports(update, repository)) {
                    return new SendMessage(chatId, "Не поддерживается");
                }
                if (!commandName.isCorrect(update)) {
                    return new SendMessage(chatId, "Не корректная");
                }
                return commandName.handle(update);
            }
        }
        return new SendMessage(chatId, "Неизвестная команда");
    }

}
