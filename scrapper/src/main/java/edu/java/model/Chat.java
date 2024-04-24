package edu.java.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "links")
@EqualsAndHashCode(exclude = "links")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "chats")
public class Chat implements Serializable {
    @Id
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JoinTable(
        name = "link_chat",
        joinColumns = @JoinColumn(name = "chatid"),
        inverseJoinColumns = @JoinColumn(name = "linkid")
    )
    private Set<Link> links = new HashSet<>();

    public Chat(long tgChatId, String name) {
        this.id = tgChatId;
        this.name = name;
    }
}
