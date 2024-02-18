package edu.java.bot.comands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    boolean isCorrect(Update update);

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }

    default boolean supports(Update update, ChatRepository repository) {
        if (command().equals(CommandInfo.START.getCommand()) || command().equals(CommandInfo.HELP.getCommand())) {
            return true;
        }
        return repository.isRegistered(update.message().chat().id());
    }
}
