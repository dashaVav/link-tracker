package edu.java.bot.utils;

import edu.java.bot.links.LinkInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.net.URI;

@Getter
@AllArgsConstructor
@Data
public class Link {
    private URI uri;
    private LinkInfo linkInfo;
    private String resource;
}
