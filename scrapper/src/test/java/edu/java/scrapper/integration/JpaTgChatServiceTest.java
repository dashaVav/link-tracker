package edu.java.scrapper.integration;

import edu.java.dto.api.request.AddChatRequest;
import edu.java.model.Chat;
import edu.java.repositoty.jpa.JpaChatRepository;
import edu.java.repositoty.jpa.JpaLinkRepository;
import edu.java.service.jpa.JpaTgChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JpaTgChatServiceTest extends JpaIntegrationEnvironment {
    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    private JpaTgChatService jpaTgChatService;

    @BeforeEach
    void setUp() {
        jpaTgChatService = new JpaTgChatService(jpaLinkRepository, jpaChatRepository);
    }

    @Test
    public void testRegisterChat() {
        long tgChatId = 111L;
        AddChatRequest addChatRequest = new AddChatRequest("testUser");

        jpaTgChatService.register(tgChatId, addChatRequest);

        assertTrue(jpaChatRepository.findById(tgChatId).isPresent());
    }

    @Test
    public void testUnregisterChat() {
        long tgChatId = 121L;
        jpaChatRepository.save(new Chat(tgChatId, "testUser"));

        jpaTgChatService.unregister(tgChatId);

        assertFalse(jpaChatRepository.findById(tgChatId).isPresent());
    }
}
