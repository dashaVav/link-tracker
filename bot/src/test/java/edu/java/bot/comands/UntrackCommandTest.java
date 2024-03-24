package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.comand.UntrackCommand;
import edu.java.bot.link.LinkHandlerChain;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UntrackCommandTest {
    private UntrackCommand untrackCommand;
    private LinkHandlerChain linkHandlerChain;

    private Update update;
    private final long chatId = 12345L;

    @BeforeEach
    void setUp() {

        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        update = mock(Update.class);
        when(update.message()).thenReturn(message);
        when(update.message().chat().id()).thenReturn(chatId);

        linkHandlerChain = mock(LinkHandlerChain.class);
        untrackCommand = new UntrackCommand(linkHandlerChain);
    }

    @Test
    void testHandle_Success() {
        String link = "https://example.com";
        when(update.message().text()).thenReturn("untrack " + link);
        when(update.message().chat().id()).thenReturn(chatId);
        when(linkHandlerChain.handleRequestUnsubscribe(chatId, URI.create(link))).thenReturn(
            "Untracking started for " + link);

        String result = untrackCommand.handle(update);

        assertEquals("Untracking started for https://example.com", result);
        verify(linkHandlerChain, times(1)).handleRequestUnsubscribe(chatId, URI.create(link));
    }

    @Test
    void testIsCorrect_True() {
        when(update.message().text()).thenReturn("untrack https://example.com");

        boolean result = untrackCommand.isCorrect(update);

        assertTrue(result);
    }

    @Test
    void testIsCorrect_False() {
        when(update.message().text()).thenReturn("untrack https://example.com extra");

        boolean result = untrackCommand.isCorrect(update);

        assertFalse(result);
    }
}
