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
import org.mockito.Mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {

    @Mock
    private ChatRepository repository;

    @Mock
    private LinkHandlerChain linkHandlerChain;

    private UntrackCommand untrackCommand;
    private Update update;

    @BeforeEach
    public void setUp() {
        repository = mock(ChatRepository.class);
        linkHandlerChain = mock(LinkHandlerChain.class);
        untrackCommand = new UntrackCommand(repository, linkHandlerChain);

        update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void testCommand() {
        Assertions.assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    public void testDescription() {
        Assertions.assertEquals("прекратить отслеживание ссылки", untrackCommand.description());
    }

    @Test
    public void testHandleWithNonexistentLink() {
        when(update.message().text()).thenReturn("/untrack https://github.com/sanyarnd");

        URI uri = URI.create("https://github.com/sanyarnd");
        Link mockLink = mock(Link.class);
        when(linkHandlerChain.handleRequestUnsubscribe(uri)).thenReturn(mockLink);
        when(repository.containsLink(1L, mockLink)).thenReturn(false);

        String expectedResponse = String.format("Ссылки %s нет в ваших подписках.", mockLink.getUri());
        String actualResponse = untrackCommand.handle(update);

        Assertions.assertEquals(expectedResponse, actualResponse);
        verify(repository, never()).removeLink(anyLong(), any(Link.class));
    }

    @Test
    public void testIsCorrectWithValidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/untrack https://example.com");

        Assertions.assertTrue(untrackCommand.isCorrect(update));
    }

    @Test
    public void testIsCorrect_WithInvalidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/untrack");

        Assertions.assertFalse(untrackCommand.isCorrect(update));
    }
}
