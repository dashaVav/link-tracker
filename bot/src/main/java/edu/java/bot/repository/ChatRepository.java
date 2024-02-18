package edu.java.bot.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRepository {
    private final HashMap<Long, List<String>> chats = new HashMap<>();

    public boolean isRegistered(Long id) {
        return chats.containsKey(id);
    }

    public void register(Long id) {
        chats.put(id, new ArrayList<>());
    }

    public void addLink(Long id, String link) {
        chats.get(id).add(link);
    }

    public void removeLink(Long id, String link) {
        chats.get(id).remove(link);
    }

    public boolean containsLink(Long id, String link) {
        return chats.get(id).contains(link);
    }

    public List<String> getList(Long id) {
        return chats.get(id);
    }
}
