package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.links.Link;
import edu.java.bot.links.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {

    @Mock
    private ChatRepository mockRepository;

    @Mock
    private LinkHandlerChain mockLinkHandlerChain;

    private UntrackCommand untrackCommand;
    private Update update;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        untrackCommand = new UntrackCommand(mockRepository, mockLinkHandlerChain);

        update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void testCommand() {
        assertEquals("/untrack", untrackCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("прекратить отслеживание ссылки", untrackCommand.description());
    }

    @Test
    public void testHandle_WithNonexistentLink() {
        when(update.message().text()).thenReturn("/untrack https://github.com/sanyarnd");

        URI uri = URI.create("https://github.com/sanyarnd");
        Link mockLink = mock(Link.class);
        when(mockLinkHandlerChain.handleRequestUnsubscribe(uri)).thenReturn(mockLink);
        when(mockRepository.containsLink(1L, mockLink)).thenReturn(false);

        String expectedResponse = String.format("Ссылки %s нет в ваших подписках.", mockLink);
        String actualResponse = untrackCommand.handle(update);

        assertEquals(expectedResponse, actualResponse);
        verify(mockRepository, never()).removeLink(anyLong(), any(Link.class));
    }

    @Test
    public void testIsCorrect_WithValidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/untrack https://example.com");

        assertTrue(untrackCommand.isCorrect(update));
    }

    @Test
    public void testIsCorrect_WithInvalidText() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/untrack");

        assertFalse(untrackCommand.isCorrect(update));
    }
}
