package edu.java.bot.links;

import java.net.URI;
import org.springframework.stereotype.Component;

@Component
public class LinkHandlerChain {
    private final LinkHandler firstHandler;

    public LinkHandlerChain() {
        firstHandler = new GitHubHandler();
        LinkHandler stackOverflowHandler = new StackOverflowHandler();
        firstHandler.setNextHandler(stackOverflowHandler);
    }

    public Link handleRequestSubscribe(URI uri) {
        return firstHandler.subscribe(uri);
    }

    public Link handleRequestUnsubscribe(URI uri) {
        return firstHandler.unsubscribe(uri);
    }
}
