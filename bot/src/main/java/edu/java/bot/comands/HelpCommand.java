package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
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
    public String handle(Update update) {
        StringBuilder helpMessage = new StringBuilder("Список доступных команд:\n");
        for (CommandInfo command : CommandInfo.values()) {
            helpMessage.append(command.getCommand()).append(" - ").append(command.getDescription())
                .append("\n");
        }
        return helpMessage.toString();
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }
}
