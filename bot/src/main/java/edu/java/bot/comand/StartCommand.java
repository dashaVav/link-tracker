package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.scrapper.request.AddChatRequest;
import edu.java.bot.exception.api.ApiBadRequestException;
import edu.java.bot.exception.api.ApiReAddingException;
import edu.java.bot.exception.api.ResourceUnavailableException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient client;
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
    public String handle(Update update) {
        String message;
        try {
            client.addChat(
                update.message().chat().id(),
                new AddChatRequest(update.message().chat().username())
            );
            message = "Регистрация прошла успешно. Добро пожаловать!";
        } catch (ApiReAddingException e) {
            message = e.getApiErrorResponse().description();
        } catch (ApiBadRequestException | ResourceUnavailableException e) {
            message = "Ошибка! Попробуйте позже!";
        }
        return message;
    }

    @Override
    public boolean isCorrect(Update update) {
        return command().equals(update.message().text());
    }
}
