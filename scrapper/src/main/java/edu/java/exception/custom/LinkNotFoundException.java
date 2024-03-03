package edu.java.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LinkNotFoundException extends CustomApiException {
    public LinkNotFoundException(String msg) {
        super("Ссылка не найдена.", HttpStatus.NOT_FOUND, msg);
    }
}
