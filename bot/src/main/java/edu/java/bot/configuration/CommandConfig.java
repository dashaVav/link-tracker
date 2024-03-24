package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.comand.EndCommand;
import edu.java.bot.comand.HelpCommand;
import edu.java.bot.comand.ListCommand;
import edu.java.bot.comand.StartCommand;
import edu.java.bot.comand.TrackCommand;
import edu.java.bot.comand.UntrackCommand;
import edu.java.bot.link.LinkHandlerChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfig {

    @Bean
    public HelpCommand helpCommand() {
        return new HelpCommand();
    }

    @Bean
    public StartCommand startCommand(ScrapperClient client) {
        return new StartCommand(client);
    }

    @Bean
    public ListCommand listCommand(ScrapperClient client) {
        return new ListCommand(client);
    }

    @Bean
    public TrackCommand trackCommand(LinkHandlerChain linkHandlerChain) {
        return new TrackCommand(linkHandlerChain);
    }

    @Bean
    public UntrackCommand untrackCommand(LinkHandlerChain linkHandlerChain) {
        return new UntrackCommand(linkHandlerChain);
    }

    @Bean
    public EndCommand endCommand(ScrapperClient client) {
        return new EndCommand(client);
    }
}
