package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.links.Link;
import edu.java.bot.links.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackCommand implements Command {
    private final ChatRepository repository;
    private final LinkHandlerChain linkHandlerChain;
    private static final CommandInfo COMMAND_INFO = CommandInfo.UNTRACK;

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
        long id = update.message().chat().id();

        URI uri = URI.create(CommandUtils.getLink(update.message().text()));
        Link link = linkHandlerChain.handleRequestUnsubscribe(uri);

        if (!repository.containsLink(id, link)) {
            return String.format("Ссылки %s нет в ваших подписках.", link);
        } else {
            repository.removeLink(id, link);
            return String.format("Ссылка %s успешно удалена.", link);
        }
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().split(" ").length == 2;
    }

}
