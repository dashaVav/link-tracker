package edu.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "links")
public class Link implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Convert(converter = URItoString.class)
    @Column(name = "url", nullable = false, unique = true)
    private URI url;

    @Column(name = "checkedat", nullable = false)
    private OffsetDateTime checkedAt;

    @ManyToMany(mappedBy = "links")
    private Set<Chat> chats = new HashSet<>();

    public Link(URI url, OffsetDateTime time) {
        this.url = url;
        this.checkedAt = time;
    }

    public Link(long id, URI url, OffsetDateTime time) {
        this.id = id;
        this.url = url;
        this.checkedAt = time;
    }
}
