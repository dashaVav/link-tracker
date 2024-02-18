package edu.java.bot.links;

import edu.java.bot.utils.Link;

public abstract class LinkHandler {
    protected LinkHandler nextHandler;

    public void setNextHandler(LinkHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public Link handleRequest(String url) {
        if (nextHandler != null) {
            nextHandler.handleRequest(url);
        }
        return null;
    }
}
