package edu.java.domain.repositoty;

import edu.java.domain.model.LinkChat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class LinkChatRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(long linkId, long chatId) {
        String sql = "insert into link_chat (linkId, chatId) values (?, ?)";
        jdbcTemplate.update(sql, linkId, chatId);
    }

    @Transactional
    public void remove(long linkId, long chatId) {
        String sql = "delete from link_chat where linkId = ? and chatId = ?";
        jdbcTemplate.update(sql, linkId, chatId);
    }

    public Integer countChatsByLinkId(long linkId) {
        String sql = "select count(*) from link_chat where linkId = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, linkId);
    }

    public List<LinkChat> findAllByLinkId(long linkId) {
        String sql = "select * from link_chat where linkId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LinkChat.class), linkId);
    }

    public List<LinkChat> findAllByChatId(long chatId) {
        String sql = "select * from link_chat where chatId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LinkChat.class), chatId);
    }

    public Optional<LinkChat> findPairLinkChat(long linkId, long chatId) {
        String sql = "select * from link_chat where linkId = ? and chatId = ?";
        List<LinkChat> linkChat = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LinkChat.class), linkId, chatId);
        return linkChat.stream().findFirst();
    }
}
