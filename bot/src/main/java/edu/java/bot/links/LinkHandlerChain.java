package edu.java.bot.links;

import edu.java.bot.utils.Link;
import org.springframework.stereotype.Component;

@Component
public class LinkHandlerChain {
    private final LinkHandler firstHandler;

    public LinkHandlerChain() {
        firstHandler = new GitHubHandler();
        LinkHandler stackOverflowHandler = new StackOverflowHandler();
        firstHandler.setNextHandler(stackOverflowHandler);
    }

    public Link handleRequest(String url) {
        return firstHandler.handleRequest(url);
    }

}
