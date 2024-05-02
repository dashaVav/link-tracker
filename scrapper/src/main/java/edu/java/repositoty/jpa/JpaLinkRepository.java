package edu.java.repositoty.jpa;

import edu.java.model.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(URI url);

    @Modifying
    @Transactional
    @Query("UPDATE Link link SET link.checkedAt = :checkedAt WHERE link.id = :id")
    void updateCheckedAtById(@Param("id") Long id, @Param("checkedAt") OffsetDateTime checkedAt);

    @Query("SELECT link FROM Link link WHERE link.checkedAt < :date")
    List<Link> findLinksToCheck(@Param("date") OffsetDateTime date);

    @Query(value = "SELECT c.chatId FROM link_chat c WHERE c.linkId = :id", nativeQuery = true)
    List<Long> findChatIdsForLink(@Param("id") Long id);
}
