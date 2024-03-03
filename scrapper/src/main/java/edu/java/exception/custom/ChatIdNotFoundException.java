package edu.java.exception.custom;


import org.springframework.http.HttpStatus;

public class ChatIdNotFoundException extends CustomApiException {
    public ChatIdNotFoundException(String msg) {
        super("Чат с указанным id не найден.", HttpStatus.NOT_FOUND, msg);
    }
}
