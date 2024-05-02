package edu.java.exception.custom;

import org.springframework.http.HttpStatus;

public class ResourceUnavailableException extends CustomApiException {
    public ResourceUnavailableException(String msg, HttpStatus code) {
        super("Попробуйте позже.", code, msg);
    }
}
