package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.Link;
import edu.java.bot.repository.ChatRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;

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
    public String handle(Update update) {
        long id = update.message().chat().id();

        List<Link> links = repository.getList(id);

        return links.isEmpty() ? "Список отслеживаемых ссылок пуст." : listOfLinks(links);
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }

    private String listOfLinks(List<Link> links) {
        return IntStream.range(0, links.size())
            .mapToObj(i -> (i + 1) + ". " + links.get(i).getUri())
            .collect(Collectors.joining("\n"));
    }
}
