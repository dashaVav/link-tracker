package edu.java.bot.configuration;

import edu.java.bot.comand.HelpCommand;
import edu.java.bot.comand.ListCommand;
import edu.java.bot.comand.StartCommand;
import edu.java.bot.comand.TrackCommand;
import edu.java.bot.comand.UntrackCommand;
import edu.java.bot.link.LinkHandlerChain;
import edu.java.bot.repository.ChatRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfig {

    @Bean
    public HelpCommand helpCommand() {
        return new HelpCommand();
    }

    @Bean
    public StartCommand startCommand(ChatRepository repository) {
        return new StartCommand(repository);
    }

    @Bean
    public ListCommand listCommand(ChatRepository repository) {
        return new ListCommand(repository);
    }

    @Bean
    public TrackCommand trackCommand(ChatRepository repository, LinkHandlerChain linkHandlerChain) {
        return new TrackCommand(repository, linkHandlerChain);
    }

    @Bean
    public UntrackCommand untrackCommand(ChatRepository repository, LinkHandlerChain linkHandlerChain) {
        return new UntrackCommand(repository, linkHandlerChain);
    }
}
