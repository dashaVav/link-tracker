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
    private static final CommandInfo COMMAND_INFO = CommandInfo.START;

    @Override
    public String command() {
        return COMMAND_INFO.getCommand();
    }

    @Override
    public String description() {
        return COMMAND_INFO.getDescription();
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.message().chat().id();
        String message;

        if (repository.isRegistered(id)) {
            message = "Извините, но данный аккаунт уже зарегистрирован.";
        } else {
            repository.register(id);
            message = "Регистрация прошла успешно. Добро пожаловать!";
        }

        return new SendMessage(id, message);
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }
}
