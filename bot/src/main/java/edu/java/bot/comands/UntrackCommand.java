package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.repository.ChatRepository;
import edu.java.bot.utils.MessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UntrackCommand implements Command {
    private final ChatRepository repository;
    private static final CommandInfo commandInfo = CommandInfo.UNTRACK;

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
            String link = MessageUtils.getLink(update.message().text());

            if (!repository.containsLink(id, link)) {
                return new SendMessage(id, String.format("Ссылки %s нет", link));
            } else {

            repository.removeLink(id, link);
            return new SendMessage(id, String.format("Ссылка %s успешно удалена.", link));}
        } catch (Exception e) {
            return new SendMessage(id, "Нужно еще ссылку добавить");
        }
    }

}
