package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.comand.EndCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EndCommandTest {
    private EndCommand endCommand;
    private ScrapperClient scrapperClient;

    @BeforeEach
    void setUp() {
        scrapperClient = mock(ScrapperClient.class);
        endCommand = new EndCommand(scrapperClient);
    }

    @Test
    void testHandle_Success() {
        long chatId = 12345L;
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);

        String result = endCommand.handle(update);

        Assertions.assertEquals("Работа с ботом успешно завершена!", result);
        verify(scrapperClient, times(1)).removeChat(chatId);
    }

    @Test
    void testIsCorrect_True() {
        long chatId = 12345L;
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("/end");

        boolean result = endCommand.isCorrect(update);

        assertTrue(result);
    }

    @Test
    void testIsCorrect_False() {
        long chatId = 12345L;
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(chat.id()).thenReturn(chatId);
        when(message.chat()).thenReturn(chat);
        Update update = mock(Update.class);
        when(update.message()).thenReturn(message);
        when(update.message().text()).thenReturn("not_end");

        boolean result = endCommand.isCorrect(update);

        assertFalse(result);
    }
}
