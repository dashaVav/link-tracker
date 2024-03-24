package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.comand.StartCommand;
import edu.java.bot.dto.scrapper.request.AddChatRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StartCommandTest {
    private StartCommand startCommand;
    private ScrapperClient scrapperClient;

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

        scrapperClient = mock(ScrapperClient.class);
        startCommand = new StartCommand(scrapperClient);
    }

    @Test
    void testHandle_Success() {
        String username = "testUser";
        when(update.message().chat().username()).thenReturn(username);

        String result = startCommand.handle(update);

        Assertions.assertEquals("Регистрация прошла успешно. Добро пожаловать!", result);
        verify(scrapperClient, times(1)).addChat(chatId, new AddChatRequest(username));
    }

    @Test
    void testIsCorrect_True() {
        when(update.message().text()).thenReturn("/start");

        boolean result = startCommand.isCorrect(update);

        assertTrue(result);
    }

    @Test
    void testIsCorrect_False() {
        when(update.message().text()).thenReturn("not_start");

        boolean result = startCommand.isCorrect(update);

        assertFalse(result);
    }
}
