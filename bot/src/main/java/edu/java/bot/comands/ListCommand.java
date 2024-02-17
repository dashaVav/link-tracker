package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ListCommand implements Command {
    private final ChatRepository repository;
    private final CommandInfo commandInfo = CommandInfo.LIST;

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
        long id = update.message().chat().id();
        if (!repository.isRegistered(id)) {
            return new SendMessage(id, "Вы не зарегистрированы. Команда недоступна.");
        }

        return new SendMessage(id, repository.getList(id).toString());
    }
}
