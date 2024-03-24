package edu.java.exception.custom;

import org.springframework.http.HttpStatus;

public class ReRegistrationException extends CustomApiException {
    public ReRegistrationException(String msg) {
        super("Вы уже зарегистрированы.", HttpStatus.CONFLICT, msg);
    }
}
