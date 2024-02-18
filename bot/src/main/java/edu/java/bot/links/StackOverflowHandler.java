package edu.java.bot.links;

import edu.java.bot.utils.Link;
import java.net.URI;

public class StackOverflowHandler extends LinkHandler {
    private final static String host = LinkInfo.STACKOVERFLOW.getHost();

    @Override
    public Link handleRequest(String url) {
        if (url.contains(host)) {
            System.out.println("Отслеживание обновлений на StackOverflow: " + url);
            return new Link(URI.create(url), LinkInfo.STACKOVERFLOW, host);
        } else {
            return super.handleRequest(url);
        }
    }
}
