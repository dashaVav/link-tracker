package edu.java.bot.links;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LinkInfo {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    private final String host;
}
