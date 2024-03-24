package edu.java.domain.repositoty;

import edu.java.model.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class JdbcLinksRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(String url) {
        jdbcTemplate.update("insert into links (url) values (?)", url);
    }

    @Transactional
    public void addRelationship(long linkId, long chatId) {
        jdbcTemplate.update("insert into link_chat (linkId, chatId) values (?, ?)", linkId, chatId);
    }

    public Optional<Link> findLinkByUrl(String url) {
        String sql = "select * from links where url = ?";
        List<Link> links = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), url);
        return links.stream().findFirst();
    }

    public Link findById(long id) {
        String sql = "select * from links where id = ?";
        List<Link> links = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), id);
        return links.getFirst();
    }

    public List<Link> findLinksToCheck(OffsetDateTime date) {
        String sql = "select * from links where checkedAt < ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), date);
    }

    public List<Link> findAllByChatId(long chatId) {
        String sql = "select l.* from links l join link_chat lc on l.id = lc.linkId where lc.chatId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), chatId);
    }

    @Transactional
    public void remove(long tgChatId, long linkId) {
        jdbcTemplate.update("delete from link_chat where linkId = ? and chatId = ?", linkId, tgChatId);
        Integer count = jdbcTemplate
            .queryForObject("select count(*) from link_chat where linkId = ?", Integer.class, linkId);

        if (count == 0) {
            jdbcTemplate.update("delete from links where id = ?", linkId);
        }
    }

    public boolean isLinkAlreadyAddedForChat(String url, long tgChatId) {
        String sql = "select exists "
            + "(select 1 from link_chat lc join links l on lc.linkId = l.id where l.url = ? and lc.chatId = ?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, url, tgChatId));
    }

    public List<Long> tgChatIdsByLinkId(long linkId) {
        String sql =
            "SELECT chats.id FROM link_chat JOIN chats ON link_chat.chatId = chats.id WHERE link_chat.linkId = ?";
        return jdbcTemplate.queryForList(sql, Long.class, linkId);
    }

    @Transactional
    public void updateCheckAt(OffsetDateTime time, long linkId) {
        String updateSql = "update links set checkedAt = ? where id = ?";
        jdbcTemplate.update(updateSql, time, linkId);
    }
}
