package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.links.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import edu.java.bot.utils.Link;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrackCommand implements Command {

    private final ChatRepository repository;
    private final LinkHandlerChain linkHandlerChain;
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

        try {
            String link = CommandUtils.getLink(update.message().text());
            Link linkAfterCheck = linkHandlerChain.handleRequest(link);

            if (linkAfterCheck == null) {
                return new SendMessage(id, String.format("Ссылка %s не поддерживается", link));
            }

            repository.addLink(id, linkAfterCheck);
            return new SendMessage(id, String.format("Ссылка %s успешно добавлена", link));
        } catch (Exception e) {
            return new SendMessage(id, "Нужно еще ссылку добавить");
        }
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().split(" ").length == 2;
    }
}
