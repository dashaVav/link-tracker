package edu.java.scrapper.integration;

import edu.java.domain.model.Chat;
import edu.java.domain.repositoty.ChatsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class JdbcChatDaoTest extends IntegrationEnvironment {

    @Autowired
    private ChatsRepository chatRepository;

    @Test
    @Transactional
//    @Rollback
    void addTest() {
        Chat chat = new Chat(1L, "Test Chat");
//        chatRepository.add(chat);
//        List<Chat> chats = chatRepository.findAll();
//        assertTrue(chats.contains(chat));
    }

    @Test
    @Transactional
//    @Rollback
    void removeTest() {
        // Предполагается, что в базе данных уже есть данные для удаления
        // Здесь нужно добавить логику для удаления и проверки
    }
}
