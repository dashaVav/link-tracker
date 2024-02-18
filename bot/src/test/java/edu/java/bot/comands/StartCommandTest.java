package edu.java.bot.comands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.comands.Command;
import edu.java.bot.comands.StartCommand;
import edu.java.bot.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartCommandTest {

    @Mock
    private ChatRepository repository;

    private Command startCommand;
    private final Update update = mock(Update.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        startCommand = new StartCommand(repository);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
    }

    @Test
    public void testCommand() {
        assertEquals("/start", startCommand.command());
    }

    @Test
    public void testDescription() {
        assertEquals("зарегистрировать пользователя", startCommand.description());
    }

    @Test
    public void testHandle_RegistrationSuccess() {
        when(update.message().chat().id()).thenReturn(1234L);
        when(repository.isRegistered(1234L)).thenReturn(false);

        String message = startCommand.handle(update);

        assertEquals("Регистрация прошла успешно. Добро пожаловать!", message);
        verify(repository).register(1234L);
    }

    @Test
    public void testHandle_AlreadyRegistered() {
        when(update.message().chat().id()).thenReturn(5678L);
        when(repository.isRegistered(5678L)).thenReturn(true);

        String message = startCommand.handle(update);

        assertEquals("Извините, но данный аккаунт уже зарегистрирован.", message);
        verify(repository, never()).register(5678L);
    }

    @Test
    public void testIsCorrect() {
        when(update.message().text()).thenReturn("/start");

        assertTrue(startCommand.isCorrect(update));
    }

    @Test
    public void testIsCorrect_WrongCommand() {
        when(update.message().text()).thenReturn("/start log");

        assertFalse(startCommand.isCorrect(update));
    }
}
