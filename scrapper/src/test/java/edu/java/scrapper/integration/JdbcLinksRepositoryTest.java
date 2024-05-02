package edu.java.scrapper.integration;

import edu.java.domain.model.Chat;
import edu.java.domain.model.Link;
import edu.java.domain.repositoty.JdbcChatsRepository;
import edu.java.domain.repositoty.JdbcLinksRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class JdbcLinksRepositoryTest extends IntegrationEnvironment {

    private static JdbcLinksRepository linksRepository;
    private static JdbcChatsRepository chatsRepository;

    private static final String testUrl = "https://example.com";
    private static final Chat testChat = new Chat(1L, "Test Chat");

    @BeforeAll
    public static void setUp() {
        linksRepository = new JdbcLinksRepository(jdbcTemplate);
        chatsRepository = new JdbcChatsRepository(jdbcTemplate);

        chatsRepository.add(testChat.getId(), testChat.getName());
        linksRepository.add(testUrl);
        linksRepository.addRelationship(1L, testChat.getId());
    }

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isPresent());
    }

    @Test
    @Transactional
    @Rollback
    void findLinkByUrlTest() {
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isPresent());
        Assertions.assertEquals(link.get().getUrl().toString(), testUrl);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        linksRepository.remove(testChat.getId(), 1L);
        Optional<Link> link = linksRepository.findLinkByUrl(testUrl);
        Assertions.assertTrue(link.isEmpty());

        linksRepository.add(testUrl);
        linksRepository.addRelationship(2L, testChat.getId());
    }

    @Test
    @Transactional
    @Rollback
    void findByIdTest() {
        Link link = linksRepository.findById(linksRepository.findLinkByUrl(testUrl).get().getId());
        Assertions.assertEquals(linksRepository.findLinkByUrl(testUrl).get().getId(), link.getId());
        Assertions.assertEquals(testUrl, link.getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    void findLinksToCheckTest() {
        List<Link> linkList = linksRepository.findLinksToCheck(OffsetDateTime.now().minusNanos(1));

        Assertions.assertEquals(1L, linkList.getFirst().getId());
        Assertions.assertEquals(testUrl, linkList.getFirst().getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    void findAllByChatIdTest() {
        List<Link> linkList = linksRepository.findAllByChatId(testChat.getId());

        Assertions.assertEquals(linksRepository.findLinkByUrl(testUrl).get().getId(), linkList.getFirst().getId());
        Assertions.assertEquals(testUrl, linkList.getFirst().getUrl().toString());
    }

    @Test
    @Transactional
    @Rollback
    void isLinkAlreadyAddedForChatTrueTest() {
        boolean isLinkAlreadyAddedForChat = linksRepository.isLinkAlreadyAddedForChat(testUrl, testChat.getId());

        Assertions.assertTrue(isLinkAlreadyAddedForChat);
    }

    @Test
    @Transactional
    @Rollback
    void isLinkAlreadyAddedForChatFalseTest() {
        boolean isLinkAlreadyAddedForChat =
            linksRepository.isLinkAlreadyAddedForChat("https://example1.com", testChat.getId());
        Assertions.assertFalse(isLinkAlreadyAddedForChat);
    }

    @Test
    @Transactional
    @Rollback
    void tgChatIdsByLinkIdTest() {
        List<Long> linkList = linksRepository.tgChatIdsByLinkId(linksRepository.findLinkByUrl(testUrl).get().getId());
        Assertions.assertEquals(1L, linkList.getFirst());
    }

    @Test
    @Transactional
    @Rollback
    void updateCheckAtTest() {
        OffsetDateTime localDateTime = OffsetDateTime.now().minusDays(1);
        Link linkAfterUpdate = new Link(1L, URI.create(testUrl), localDateTime);

        linksRepository.updateCheckAt(localDateTime, 1L);

        Link link = linksRepository.findById(1L);
        Assertions.assertEquals(link.getCheckedAt().getMinute(), linkAfterUpdate.getCheckedAt().getMinute());
    }

}
