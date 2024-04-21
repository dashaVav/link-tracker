package edu.java.scrapper.integration;


import edu.java.model.Chat;
import java.util.Optional;
import edu.java.repositoty.jdbc.JdbcChatsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcChatsRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JdbcChatsRepository chatsRepository;

    private static final Chat testChat = new Chat(11L, "Test Chat");

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    public void cleanUp() {
        Cache cache = cacheManager.getCache("rate-limit-buckets-scrapper");
        if (cache != null) {
            cache.clear();
        }
    }

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
