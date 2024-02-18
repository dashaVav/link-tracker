package edu.java.bot.links;

import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class Link {
    private LinkInfo linkInfo;
    private URI uri;
    private String resource;
}
