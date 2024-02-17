package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.MessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackCommand implements Command{

    private final ChatRepository repository;
    private static final CommandInfo commandInfo = CommandInfo.TRACK;

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

        String link = MessageUtils.getLink(update.message().text());
        repository.addLink(id, link);
        return new SendMessage(id, String.format("Ссылка %s успешно добавлена", link));
    }
}
