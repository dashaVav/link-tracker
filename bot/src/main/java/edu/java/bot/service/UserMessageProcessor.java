package edu.java.bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.comands.Command;
import edu.java.bot.utils.MessageUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMessageProcessor {

    @Autowired
    private List<Command> commands;

    public SendMessage handleUpdate(Update update) {
            Long chatId = update.message().chat().id();
            String commandFromChat = MessageUtils.getCommand(update.message().text());


            for (Command commandName : commands) {
                if (commandName.command().equals(commandFromChat)) {
                    return commandName.handle(update);
                }
            }
            return new SendMessage(chatId, "Неизвестная команда");
    }

}
