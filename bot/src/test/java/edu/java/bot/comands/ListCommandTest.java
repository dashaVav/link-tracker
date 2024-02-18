package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.comands.ListCommand;
import edu.java.bot.links.Link;
import edu.java.bot.links.LinkInfo;
import edu.java.bot.repository.ChatRepository;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListCommandTest {
    private ListCommand listCommand;
    private ChatRepository repository;
    private final Update update = mock(Update.class);
    private final Message message = mock(Message.class);

    @BeforeEach
    public void setUp() {
        repository = mock(ChatRepository.class);
        listCommand = new ListCommand(repository);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        when(repository.getList(1L)).thenReturn(new ArrayList<>());

    }

    @Test
    public void testHandleWithEmptyList() {
        String result = listCommand.handle(update);
        assertEquals("Список отслеживаемых ссылок пуст.", result);
    }

    @Test
    public void testHandleWithNonEmptyList() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(
            LinkInfo.GITHUB,
            URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/"),
            "github.com"
        ));
        when(repository.getList(1L)).thenReturn(links);

        String result = listCommand.handle(update);

        assertEquals("1. https://github.com/sanyarnd/tinkoff-java-course-2023/\n", result);
    }

    @Test
    public void testIsCorrect() {
        when(update.message().text()).thenReturn("/list");

        boolean result = listCommand.isCorrect(update);

        assertEquals(true, result);
    }

    @Test
    public void testIsNotCorrect() {
        when(update.message().text()).thenReturn("/list list");

        boolean result = listCommand.isCorrect(update);

        assertFalse(result);
    }

    @Test
    public void testCommand() {
        assertEquals("/list", listCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("показать список отслеживаемых ссылок", listCommand.description());
    }
}
