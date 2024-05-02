package edu.java.bot.comand;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandInfo {
    START("/start", "зарегистрировать пользователя"),
    HELP("/help", " вывести окно с командами"),
    TRACK("/track", "начать отслеживание ссылки"),
    UNTRACK("/untrack", "прекратить отслеживание ссылки"),
    LIST("/list", "показать список отслеживаемых ссылок"),
    END("/end", "завершить работу");

    private final String command;
    private final String description;
}
