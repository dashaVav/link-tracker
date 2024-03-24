package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.comand.ListCommand;
import edu.java.bot.dto.api.ApiErrorResponse;
import edu.java.bot.dto.scrapper.response.LinkResponse;
import edu.java.bot.dto.scrapper.response.ListLinksResponse;
import edu.java.bot.exception.api.ApiBadRequestException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {
    private ListCommand listCommand;
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
        listCommand = new ListCommand(scrapperClient);
    }

    @Test
    void testHandle_Success() {
        ListLinksResponse linksResponse = new ListLinksResponse(List.of(
                new LinkResponse(1L, URI.create("https://github.com/dashaVav/link-tracker"))), 1
        );
        when(scrapperClient.getLinks(chatId)).thenReturn(ResponseEntity.ok(linksResponse));

        String result = listCommand.handle(update);

        Assertions.assertEquals("1. https://github.com/dashaVav/link-tracker", result);
    }

    @Test
    void testHandle_EmptyList() {
        ListLinksResponse linksResponse = new ListLinksResponse(List.of(), 0);
        when(scrapperClient.getLinks(chatId)).thenReturn(ResponseEntity.ok(linksResponse));

        String result = listCommand.handle(update);

        Assertions.assertEquals("Список отслеживаемых ссылок пуст.", result);
    }

    @Test
    void testHandle_ApiBadRequestException() {
        when(scrapperClient.getLinks(chatId)).thenThrow(new ApiBadRequestException(mock(ApiErrorResponse.class)));

        String result = listCommand.handle(update);

        Assertions.assertEquals("Ошибка! Попробуйте позже!", result);
    }

    @Test
    void testIsCorrect_True() {
        when(update.message().text()).thenReturn("/list");

        boolean result = listCommand.isCorrect(update);

        Assertions.assertTrue(result);
    }

    @Test
    void testIsCorrect_False() {
        when(update.message().text()).thenReturn("not_list");

        boolean result = listCommand.isCorrect(update);

        Assertions.assertFalse(result);
    }
}
