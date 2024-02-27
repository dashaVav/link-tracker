package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.links.Link;
import edu.java.bot.links.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TrackCommandTest {
    @Mock
    private ChatRepository repository;

    @Mock
    private LinkHandlerChain linkHandlerChain;

    private TrackCommand trackCommand;
    private Update update;

    @BeforeEach
    public void setUp() {
        repository = mock(ChatRepository.class);
        linkHandlerChain = mock(LinkHandlerChain.class);
        trackCommand = new TrackCommand(repository, linkHandlerChain);

        update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void testCommand() {
        Assertions.assertEquals("/track", trackCommand.command());
    }

    @Test
    public void testDescription() {
        Assertions.assertEquals("начать отслеживание ссылки", trackCommand.description());
    }

    @Test
    public void testHandle_WithSupportedLink() {

        when(update.message().text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        URI uri = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/");
        Link mockLink = mock(Link.class);
        when(linkHandlerChain.handleRequestSubscribe(uri)).thenReturn(mockLink);

        String expectedResponse = "Ссылка https://github.com/sanyarnd/tinkoff-java-course-2023/ успешно добавлена.";
        String actualResponse = trackCommand.handle(update);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testHandle_WithUnsupportedLink() {
        when(update.message().text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        URI uri = URI.create("https://github.com/sanyarnd/tinkoff-java-course-2023/");
        when(linkHandlerChain.handleRequestSubscribe(uri)).thenReturn(null);

        String expectedResponse =
            "Извините, ссылка https://github.com/sanyarnd/tinkoff-java-course-2023/ не поддерживается.";
        String actualResponse = trackCommand.handle(update);

        Assertions.assertEquals(expectedResponse, actualResponse);
        verify(repository, never()).addLink(anyLong(), ArgumentMatchers.any(Link.class));
    }

    @Test
    public void testIsCorrect_WithValidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/track https://github.com/sanyarnd/tinkoff-java-course-2023/");

        Assertions.assertTrue(trackCommand.isCorrect(update));
    }

    @Test
    public void testIsCorrect_WithInvalidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/track");

        Assertions.assertFalse(trackCommand.isCorrect(update));
    }
}
