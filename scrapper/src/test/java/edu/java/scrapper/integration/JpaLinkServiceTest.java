package edu.java.scrapper.integration;

import edu.java.model.Chat;
import edu.java.model.Link;
import edu.java.repositoty.jpa.JpaChatRepository;
import edu.java.repositoty.jpa.JpaLinkRepository;
import edu.java.service.jpa.JpaLinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JpaLinkServiceTest extends JpaIntegrationEnvironment {

    private JpaLinkService jpaLinkService;

    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @BeforeEach
    void setUp() {
        jpaLinkService = new JpaLinkService(jpaLinkRepository, jpaChatRepository);
    }

    @Test
    public void testAddLink() {
        long tgChatId = 11L;
        URI url = URI.create("http://testAddLink.com");
        jpaChatRepository.save(new Chat(tgChatId, "testUser1"));

        Link link = jpaLinkService.add(tgChatId, url);

        Chat chat = jpaChatRepository.findById(tgChatId).get();
        assertNotNull(link);
        assertEquals(url, link.getUrl());
        assertTrue(jpaLinkRepository.findByUrl(url).isPresent());
        System.out.println(link);
        System.out.println(chat.getLinks());
        assertTrue(chat.getLinks().stream().map(Link::getUrl).toList().contains(link.getUrl()));
    }

    @Test
    public void testRemoveLink() {
        long tgChatId = 12L;
        URI url = URI.create("http://testRemoveLink.com");
        jpaChatRepository.save(new Chat(tgChatId, "testUser2"));
        Chat chat = jpaChatRepository.findById(tgChatId).get();
        Link link = jpaLinkRepository.save(new Link(url, OffsetDateTime.now()));
        chat.getLinks().add(link);
        jpaChatRepository.save(chat);

        jpaLinkService.remove(tgChatId, url);

        assertTrue(jpaLinkRepository.findByUrl(url).isEmpty());
        assertTrue(jpaChatRepository.findById(tgChatId).get().getLinks().isEmpty());
    }

    @Test
    public void testListAllLinks() {
        long tgChatId = 13L;
        URI url1 = URI.create("http://example1.com");
        URI url2 = URI.create("http://example2.com");
        jpaChatRepository.save(new Chat(tgChatId, "testUser3"));
        Link link1 = jpaLinkRepository.save(new Link(url1, OffsetDateTime.now()));
        Link link2 = jpaLinkRepository.save(new Link(url2, OffsetDateTime.now()));
        Chat chat = jpaChatRepository.findById(tgChatId).get();
        chat.getLinks().addAll(List.of(link1, link2));
        jpaChatRepository.save(chat);

        List<Link> links = jpaLinkService.listAll(tgChatId);

        assertEquals(2, links.size());
        assertTrue(links.stream().anyMatch(link -> link.getUrl().equals(url1)));
        assertTrue(links.stream().anyMatch(link -> link.getUrl().equals(url2)));
    }
}
