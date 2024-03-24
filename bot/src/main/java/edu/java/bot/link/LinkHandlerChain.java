package edu.java.bot.link;

import java.net.URI;
import edu.java.bot.client.ScrapperClient;
import org.springframework.stereotype.Component;

@Component
public class LinkHandlerChain {
    private final LinkHandler firstHandler;

    public LinkHandlerChain(ScrapperClient client) {
        firstHandler = new GitHubHandler(client);
        LinkHandler stackOverflowHandler = new StackOverflowHandler(client);
        firstHandler.setNextHandler(stackOverflowHandler);
    }

    public String handleRequestSubscribe(long tgChatId, URI uri) {
        return firstHandler.subscribe(tgChatId, uri);
    }

    public String handleRequestUnsubscribe(long tgChatId, URI uri) {
        return firstHandler.unsubscribe(tgChatId, uri);
    }
}
