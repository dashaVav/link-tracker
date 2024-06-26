package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comand.Command;
import edu.java.bot.utils.CommandUtils;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMessageProcessor {

    private List<Command> commands;

    public SendMessage handleUpdate(Update update) {
        Long chatId = update.message().chat().id();
        String commandFromChat = CommandUtils.getCommand(update.message().text());

        for (Command commandName : commands) {
            if (commandName.command().equals(commandFromChat)) {
                if (!commandName.isCorrect(update)) {
                    return new SendMessage(chatId, "Команда введена не корректно.");
                }
                return new SendMessage(chatId, commandName.handle(update));
            }
        }
        return new SendMessage(chatId, "Неизвестная команда.");
    }

}
