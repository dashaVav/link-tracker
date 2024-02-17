package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final CommandInfo commandInfo = CommandInfo.HELP;

    @Override
    public String command() {
        return commandInfo.getCommand();
    }

    @Override
    public String description() {
        return commandInfo.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        String helpMessage = "Список доступных команд:\n" +
            "/start - зарегистрировать пользователя\n" +
            "/help - вывести окно с командами\n" +
            "/track - начать отслеживание ссылки\n" +
            "/untrack - прекратить отслеживание ссылки\n" +
            "/list - показать список отслеживаемых ссылок";
        return new SendMessage(update.message().chat().id(), helpMessage);
    }
}
