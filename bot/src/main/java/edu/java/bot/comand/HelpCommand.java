package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import java.util.Arrays;
import java.util.stream.Collectors;

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
        return Arrays.stream(CommandInfo.values())
            .map(command -> command.getCommand() + " - " + command.getDescription())
            .collect(Collectors.joining("\n", "Список доступных команд:\n", "\n"));
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }
}
