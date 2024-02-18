package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.links.Link;
import edu.java.bot.repository.ChatRepository;
import java.util.List;
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
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < links.size(); i++) {
            list.append(i + 1).append(". ").append(links.get(i).getUri()).append("\n");
        }
        return list.toString();
    }
}
