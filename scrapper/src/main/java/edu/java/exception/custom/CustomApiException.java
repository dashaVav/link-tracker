package edu.java.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomApiException extends RuntimeException {
    private final String description;
    private final HttpStatus httpStatus;

    public CustomApiException(String description, HttpStatus httpStatus, String message) {
        super(message);
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
