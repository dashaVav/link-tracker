package edu.java.domain.repositoty;

import edu.java.domain.model.Link;
import java.time.LocalDateTime;
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
public class LinksRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void add(String url) {
        String sql = "insert into links (url) values (?)";
        jdbcTemplate.update(sql, url);
    }

    @Transactional
    public void remove(long linkId) {
        String sql = "delete from links where id = ?";
        jdbcTemplate.update(sql, linkId);
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

    public Long getLinkIdByUrl(String url) {
        String selectLinkIdSql = "select id from links where url = ?";
        return jdbcTemplate.queryForObject(selectLinkIdSql, Long.class, url);
    }

    @Transactional
    public void updateCheckAt(OffsetDateTime time, String url) {
        String updateSql = "update links set checkedAt = ? where url = ?";
        jdbcTemplate.update(updateSql, time, url);
    }

    public List<Link> findLinksToCheck(LocalDateTime date) {
        String sql = "select * from links where checkedAt < ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Link.class), date);
    }
}
