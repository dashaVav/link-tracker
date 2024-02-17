package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartCommand implements Command {
    private final ChatRepository repository;
    private static final CommandInfo commandInfo = CommandInfo.START;

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

        if (repository.isRegistered(id)) {
            return new SendMessage(id, "Вы уже зарегистрированы.");
        } else {
            repository.register(id);
            return new SendMessage(id, "Регистрация прошла успешно!");
        }
    }
}
