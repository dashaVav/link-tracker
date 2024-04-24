package edu.java.scrapper.integration;

import edu.java.model.Link;
import edu.java.repositoty.jpa.JpaLinkRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JpaLinkRepositoryTest extends JpaIntegrationEnvironment {

    @Autowired
    private JpaLinkRepository jpaLinkRepository;

    @Test
    public void testFindByUrl() {
        URI url = URI.create("http://testFindByUrl.com");
        Link link = new Link(url, OffsetDateTime.now());
        jpaLinkRepository.save(link);

        Optional<Link> foundLink = jpaLinkRepository.findByUrl(url);
        assertTrue(foundLink.isPresent());
        assertEquals(url, foundLink.get().getUrl());
    }

    @Test
    public void testUpdateCheckedAtById() {
        Link link = new Link(URI.create("http://testUpdateCheckedAtById"), OffsetDateTime.now());
        jpaLinkRepository.save(link);

        OffsetDateTime newCheckedAt = OffsetDateTime.now().plusHours(1);
        jpaLinkRepository.updateCheckedAtById(link.getId(), newCheckedAt);

        Optional<Link> updatedLink = jpaLinkRepository.findById(link.getId());
        assertTrue(updatedLink.isPresent());
        assertEquals(newCheckedAt.getMinute(), updatedLink.get().getCheckedAt().getMinute());
    }

    @Test
    public void testFindLinksToCheck() {
        OffsetDateTime now = OffsetDateTime.now();
        Link link1 = new Link(URI.create("http://testFindLinksToCheck1.com"), now.minusHours(2));
        Link link2 = new Link(URI.create("http://testFindLinksToCheck2.com"), now.minusHours(1));
        jpaLinkRepository.saveAll(List.of(link1, link2));

        List<Link> linksToCheck = jpaLinkRepository.findLinksToCheck(now.minusMinutes(30));
        assertEquals(2, linksToCheck.size());
        assertTrue(linksToCheck.stream()
            .anyMatch(link -> link.getUrl().equals(URI.create("http://testFindLinksToCheck1.com"))));
        assertTrue(linksToCheck.stream()
            .anyMatch(link -> link.getUrl().equals(URI.create("http://testFindLinksToCheck2.com"))));
    }

    @Test
    public void testFindChatIdsForLink() {
        Long linkId = 13L;
        List<Long> chatIds = jpaLinkRepository.findChatIdsForLink(linkId);
        assertTrue(chatIds.isEmpty());
    }
}
