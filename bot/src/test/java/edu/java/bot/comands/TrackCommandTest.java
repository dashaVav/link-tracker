package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.comand.TrackCommand;
import edu.java.bot.link.LinkHandlerChain;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrackCommandTest {
    private TrackCommand trackCommand;
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
        trackCommand = new TrackCommand(linkHandlerChain);
    }

    @Test
    void testHandle_Success() {
        String link = "https://example.com";
        when(update.message().text()).thenReturn("track " + link);
        when(update.message().chat().id()).thenReturn(chatId);
        when(linkHandlerChain.handleRequestSubscribe(chatId, URI.create(link))).thenReturn(
            "Tracking started for " + link);

        String result = trackCommand.handle(update);

        Assertions.assertEquals("Tracking started for https://example.com", result);
        verify(linkHandlerChain, times(1)).handleRequestSubscribe(chatId, URI.create(link));
    }

    @Test
    void testIsCorrect_True() {
        when(update.message().text()).thenReturn("track https://example.com");

        boolean result = trackCommand.isCorrect(update);

        Assertions.assertTrue(result);
    }

    @Test
    void testIsCorrect_False() {
        when(update.message().text()).thenReturn("track https://example.com extra");

        boolean result = trackCommand.isCorrect(update);

        Assertions.assertFalse(result);
    }
}
