package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import edu.java.bot.exception.api.ApiBadRequestException;
import edu.java.bot.exception.api.ApiNotFoundException;
import edu.java.bot.exception.api.ResourceUnavailableException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient client;
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
    public String handle(Update update) {
        String message;
        try {
            ListLinksResponse links = client.getLinks(update.message().chat().id()).getBody();
            message = links.size() == 0 ? "Список отслеживаемых ссылок пуст." : listOfLinks(links);
        } catch (ApiNotFoundException e) {
            message = e.getApiErrorResponse().description();
        } catch (ApiBadRequestException | ResourceUnavailableException e) {
            message = "Ошибка! Попробуйте позже!";
        }
        return message;
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }

    private String listOfLinks(ListLinksResponse listLinksResponse) {
        return IntStream.range(0, listLinksResponse.size())
            .mapToObj(i -> (i + 1) + ". " + listLinksResponse.links().get(i).url())
            .collect(Collectors.joining("\n"));
    }
}
