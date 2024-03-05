package edu.java.bot.link;

import java.net.URI;
import lombok.Setter;

@Setter public abstract class LinkHandler {
    protected LinkHandler nextHandler;

    public Link subscribe(URI uri) {
        if (nextHandler != null) {
            return nextHandler.subscribe(uri);
        }
        return null;
    }

    public Link unsubscribe(URI uri) {
        if (nextHandler != null) {
            return nextHandler.subscribe(uri);
        }
        return null;
    }
}
