package edu.java.scrapper.integration;

import edu.java.domain.model.Chat;
import edu.java.domain.repositoty.JdbcChatsRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcChatsRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatsRepository chatsRepository;

    private static final Chat testChat = new Chat(11L, "Test Chat");

    @Test
    @Transactional
    @Rollback
    void addTest() {
        chatsRepository.add(testChat.getId(), testChat.getName());
        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertEquals(chat.get(), testChat);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        chatsRepository.add(testChat.getId(), testChat.getName());

        chatsRepository.remove(testChat.getId());
        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertTrue(chat.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    void findChatById() {
        chatsRepository.add(testChat.getId(), testChat.getName());

        Optional<Chat> chat = chatsRepository.findChatById(testChat.getId());
        Assertions.assertTrue(chat.isPresent());
        Assertions.assertEquals(testChat.getName(), chat.get().getName());
    }
}
