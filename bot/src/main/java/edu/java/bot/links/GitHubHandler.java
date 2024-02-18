package edu.java.bot.links;

import java.net.URI;

public class GitHubHandler extends LinkHandler {
    private final static LinkInfo LINK_INFO = LinkInfo.GITHUB;

    @Override
    public Link subscribe(URI uri) {
        if (uri.getHost().equals(LINK_INFO.getHost())) {
            return new Link(LinkInfo.GITHUB, uri, LINK_INFO.getHost());
        } else {
            return super.subscribe(uri);
        }
    }

    @Override
    public Link unsubscribe(URI uri) {
        if (uri.getHost().equals(LINK_INFO.getHost())) {
            return new Link(LinkInfo.GITHUB, uri, LINK_INFO.getHost());
        } else {
            return super.unsubscribe(uri);
        }
    }
}
