package edu.java.exception.custom;

import org.springframework.http.HttpStatus;

public class ReRegistrationException extends CustomApiException {
    public ReRegistrationException(String msg) {
        super("Попытка повторной регистрации.", HttpStatus.CONFLICT, msg);
    }
}
