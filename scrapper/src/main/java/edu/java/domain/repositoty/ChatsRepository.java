package edu.java.domain.repositoty;

import edu.java.domain.model.Chat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ChatsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(long tgChatId, String name) {
        String sql = "insert into chats (id, name) values (?, ?)";
        jdbcTemplate.update(sql, tgChatId, name);
    }

    @Transactional
    public void remove(long tgChatId) {
        String sql = "delete from chats where id = ?";
        jdbcTemplate.update(sql, tgChatId);
    }

    public Optional<Chat> findChatById(long tgChatId) {
        String sql = "select * from chats where id = ?";
        List<Chat> chats = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Chat.class), tgChatId);
        return chats.stream().findFirst();
    }
}

