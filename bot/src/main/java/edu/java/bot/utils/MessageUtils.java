package edu.java.bot.utils;

public final class MessageUtils {
    public static String getCommand(String str) {
        return str.split(" ")[0];
    }

    public static String getLink(String str) {
        return str.split(" ")[1];
    }
}
