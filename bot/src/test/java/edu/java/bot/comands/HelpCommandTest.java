package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelpCommandTest {
    private HelpCommand helpCommand;
    private Update update;

    @BeforeEach
    public void setUp() {
        helpCommand = new HelpCommand();

        update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void testCommand() {
        Assertions.assertEquals("/help", helpCommand.command());
    }

    @Test
    public void testDescription() {
        Assertions.assertEquals(" вывести окно с командами", helpCommand.description());
    }

    @Test
    public void testHandle() {
        String sendMessage = helpCommand.handle(update);

        Assertions.assertNotNull(sendMessage);

        Assertions.assertTrue(sendMessage.startsWith("Список доступных команд:"));
        Assertions.assertTrue(sendMessage.contains("/start - зарегистрировать пользователя"));
        Assertions.assertTrue(sendMessage.contains("/help -  вывести окно с командами"));
        Assertions.assertTrue(sendMessage.contains("/track - начать отслеживание ссылки"));
        Assertions.assertTrue(sendMessage.contains("/untrack - прекратить отслеживание ссылки"));
        Assertions.assertTrue(sendMessage.contains("/list - показать список отслеживаемых ссылок"));
    }

    @Test
    public void testIsCorrect() {
        Assertions.assertTrue(helpCommand.isCorrect(update));
    }
}
