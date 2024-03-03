package edu.java.bot.comand;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.link.Link;
import edu.java.bot.link.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.CommandUtils;
import java.net.URI;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrackCommand implements Command {

    private final ChatRepository repository;
    private final LinkHandlerChain linkHandlerChain;
    private static final CommandInfo COMMAND_INFO = CommandInfo.TRACK;

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
        Link link = linkHandlerChain.handleRequestSubscribe(uri);

        if (link == null) {
            return String.format("Извините, ссылка %s не поддерживается.", uri);
        }

        repository.addLink(id, link);
        return String.format("Ссылка %s успешно добавлена.", uri);
    }

    @Override
    public boolean isCorrect(Update update) {
        return update.message().text().split(" ").length == 2;
    }
}
