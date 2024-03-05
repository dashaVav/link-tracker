package edu.java.bot.link;

import java.net.URI;

public class StackOverflowHandler extends LinkHandler {
    private final static LinkInfo RESOURCE = LinkInfo.STACKOVERFLOW;

    @Override
    public Link subscribe(URI uri) {
        if (uri.getHost().equals(RESOURCE.getHost())) {
            return new Link(LinkInfo.STACKOVERFLOW, uri, RESOURCE.getHost());
        } else {
            return super.subscribe(uri);
        }
    }

    @Override
    public Link unsubscribe(URI uri) {
        if (uri.getHost().equals(RESOURCE.getHost())) {
            return new Link(LinkInfo.GITHUB, uri, RESOURCE.getHost());
        } else {
            return super.unsubscribe(uri);
        }
    }
}
