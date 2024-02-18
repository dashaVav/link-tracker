package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.Link;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

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
            return new SendMessage(id, "Увы, но эта команда доступна только для зарегистрированных пользователей."
                + " Пожалуйста, зарегистрируйтесь для доступа к ней.");
        }

        List<Link> links = repository.getList(id);
        String result = links.isEmpty() ? "Список отслеживаемых ссылок пуст." : listOfLinks(links);

        return new SendMessage(id, result);
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().equals(command());
    }

    private String listOfLinks(List<Link> links) {
        int number = 1;
        StringBuilder list = new StringBuilder();

        for (Link link: links) {
            list.append(number).append(". ").append(link.getUri()).append("\n");
        }
        return list.toString();
    }
}
