package edu.java.bot.repository;

import edu.java.bot.links.Link;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepository {
    private final HashMap<Long, List<Link>> chats = new HashMap<>();

    public boolean isRegistered(Long id) {
        return chats.containsKey(id);
    }

    public void register(Long id) {
        chats.put(id, new ArrayList<>());
    }

    public void addLink(Long id, Link link) {
        chats.get(id).add(link);
    }

    public void removeLink(Long id, Link link) {
        chats.get(id).remove(link);
    }

    public boolean containsLink(Long id, Link link) {
        return chats.get(id).contains(link);
    }

    public List<Link> getList(Long id) {
        return chats.get(id);
    }
}
