package edu.java.bot.link;

import edu.java.bot.client.ScrapperClient;
import java.net.URI;
import lombok.Setter;

@Setter
public abstract class LinkHandler {
    protected LinkHandler nextHandler;
    public ScrapperClient client;

    LinkHandler(ScrapperClient client) {
        this.client = client;
    }

    public String subscribe(long tgChatId, URI uri) {
        if (nextHandler != null) {
            return nextHandler.subscribe(tgChatId, uri);
        }
        return notSupport(uri);
    }

    public String unsubscribe(long tgChatId, URI uri) {
        if (nextHandler != null) {
            return nextHandler.subscribe(tgChatId, uri);
        }
        return notSupport(uri);
    }

    private String notSupport(URI uri) {
        return String.format("Извините, ссылка %s не поддерживается.", uri);
    }
}
